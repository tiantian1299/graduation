<template>
	<view>
		<image src="../../static/register.jpg" mode="widthFix" class="logo"></image>
		<view class="register-container">
			<input type="text" placeholder="请输入你的邀请码" class="register-code" maxlength="6" v-model="registerCode" />
			<view class="register-desc">管理员创建员工证号之后，你可以从你的个人邮箱中获得注册邀请码</view>
			<button class="register-btn" @tap="register()">执行注册</button>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			registerCode: '',
			code: '',
			nickName: '',
			avatarUrl: ''
		};
	},
	methods: {
		register: function() {
			// this 写到外面属于Vue对象 ，写到回调函数里属于回调函数对象
			let that = this;
			if (that.registerCode == null || that.registerCode.length == 0) {
				uni.showToast({
					icon: 'none',
					title: '邀请码不能为空'
				});
				return;
			} else if (/^[0-9]{6}$/.test(that.registerCode) == false) {
				uni.showToast({
					icon: 'none',
					title: '激活码必须是6位数字'
				});
			}
			uni.login({
				provider: 'weixin',
				success: function(resp) {
					//临时授权字符串
					let code = resp.code;
					that.code = code;
					console.log(code);
				}
			});
			uni.getUserProfile({
				desc: 'weixin',
				success: function(resp) {
					// 获取用户昵称
					that.nickName = resp.userInfo.nickName;
					// 获取用户头像地址
					that.avatarUrl = resp.userInfo.avatarUrl;
					console.log(that.nickName);
					console.log(that.avatarUrl);
					let data = {
						code: that.code,
						nickname: that.nickName,
						photo: that.avatarUrl,
						registerCode: that.registerCode
					};
					that.ajax(that.url.register, 'POST', data, function(resp) {
						let permission = resp.data.permission;
						uni.setStorageSync('permission', permission);
						console.log(permission);
						uni.switchTab({
							url:"../index/index"
						})
					});
				}
			});
		}
	}
};
</script>

<style lang="less">
@import url('register.less');
</style>
