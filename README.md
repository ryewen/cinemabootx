# cinemabootx
电影购票系统 (Spring, Spring Boot, MyBatis, MySQL, Redis, Dubbo, Hystrix, Seata, Nginx)<br />
1.启动Zookeeper和Seata server(1.2).<br />
2.配置并启动Nginx.<br />
3.导入sql.<br />
4.将html文件夹放入D:\www\cinemaboot\html.<br />
5.依次启动UserApplication, MovieApplication, FieldApplication, OrderApplication, GatewayApplication.<br />
6.访问http://localhost/resources/login.html （注册时GatewayApplication控制台会输出短信验证码）.<br />
