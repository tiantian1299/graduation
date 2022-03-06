import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
	...App
})


app.$mount()

//定义全局变量
let baseUrl = "http://localhost:8080"
//prototype 是所有js共有的属性
Vue.prototype.url = {
	register: baseUrl + "/user/register",
	login:baseUrl+"/user/login",
}

//封装ajax
Vue.prototype.ajax = function(url,method,data,fun){
	uni.request({
		"url":url,
		"method":method,
		"header":{
			token:uni.getStorageSync("token")
		},
		"data":data,
		success:function(resp){
			if(resp.statusCode == 401){
				//用户未登录
				uni.redirectTo({
					url:"/pages/login/login.vue"
				})
			}
			//resp.statusCode http的状态码， resp.data.code封装的R对象的状态码
			else if(resp.statusCode == 200&&resp.data.code==200){
				let data = resp.data;
				//判断响应中的数据有没有令牌，如果有令牌就保存到缓存中
				if(data.hasOwnProperty("token")){
					let token = data.token
					console.log(token)
					uni.setStorageSync("token",token)
				}
				fun(resp)
			}
			else{
				uni.showToast({
					icon:"none",
					title:resp.data
				})
			}
		}
		
	})
}
