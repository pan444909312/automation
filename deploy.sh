#!/bin/bash
# 查找并结束进程
ps -efww|grep -w 'automation-web.jar'|grep -v grep|cut -c 9-15|xargs kill -9
# 启动服务
nohup java -jar ./automation-web.jar --spring.profiles.active=test --server.portt
=9080 >>automation.log 2>&1 &
echo 'automation-web  Server is running...

#######   通过阿里云效访问接口 #######
# input your command here
# echo 开始触发接口自动化执行
# -H "User-Agent: automation"必须添加参数，网络层面做了限制
# curl -H "User-Agent: automation" https://automation.hungrypanda.it:8443/api/testCase/debugScenarios

