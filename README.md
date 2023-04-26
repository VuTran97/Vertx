# Vertx
for cluster deploy:
1. Run 1 instance of ClusterSenderStarter
![image](https://user-images.githubusercontent.com/81553669/234509724-c7ca9df8-1c6b-47cf-ad9f-0b7adea144a0.png)
2. Run 2 instances of ClusterReceiverStarter
![image](https://user-images.githubusercontent.com/81553669/234510052-755d8206-b114-41a9-bff5-3378fd621202.png)
![image](https://user-images.githubusercontent.com/81553669/234510219-8faf224a-d408-4f15-bd6d-d7f986495e28.png)
3. Use postman call api /sendForAll of step 1
![image](https://user-images.githubusercontent.com/81553669/234510844-b3b2ae56-3f5b-43b2-9638-b1e086fda10f.png)
4. See the log console of 2 instances ClusterReceiverStarter, you can see as below
![image](https://user-images.githubusercontent.com/81553669/234511047-47fa46da-4751-4685-8e5a-3f915a64d80a.png)
![image](https://user-images.githubusercontent.com/81553669/234511142-9a4e1fc4-baf2-4578-a744-06cdf1974bd8.png)
