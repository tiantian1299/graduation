<template>
	<view>
		<view>{{ username }}</view>
		<view v-for="one in tel">{{ one }}</view>
		<view v-if="age >= 18"><button @tap="signUp()">我要报名</button></view>
		<view><input type="text" v-model="address" placeholder="请输入地址" /></view>
		<view>{{address}}</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			username: 'tiantian',
			tel: ['12312345', '12346423', '13122344'],
			age: 20,
			address: "上海市"
		};
	},
	methods: {
		signUp: function() {
			uni.showToast({
				title: '点击了我要报名'
			});
		}
	}
};
</script>

<style></style>
