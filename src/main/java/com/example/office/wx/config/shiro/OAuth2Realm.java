package com.example.office.wx.config.shiro;

import com.example.office.wx.db.pojo.TbUser;
import com.example.office.wx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    /**
     * 判断传来的token 是否是封装好的令牌对象
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {

        return token instanceof OAuth2Token;
    }


    /**
     * 授权（验证权限）
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        TbUser user  = (TbUser)principalCollection.getPrimaryPrincipal();
        int userId = user.getId();
        //查询用户的权限列表
        Set<String> permissions = userService.searchUserPermissions(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //把权限列表添加到info对象中
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 认证（验证登录时调用）
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从令牌中获取userId
        String accessToken = (String) token.getPrincipal();
        int userId = jwtUtil.getUserId(accessToken);
        // 查询用户信息
        TbUser user = userService.searchById(userId);
        if (user == null) {
            //用户离职
            throw new LockedAccountException("账号已被锁定，请联系管理员");
        }
        // 封装认证对象
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
