# Test-Case-Frame
由于测试人员需要写测试用例步骤，而一大部的步骤很可能相同，如：
- 打开网站，输入网址
- 正确输入用户名和密码，点击登录
- 在运营管理系统下拉框选择点击权限管理系统
- 点击权限组管理
- 点击添加权限组
- 权限组编码输入12345678（数字）
- 其他输入框填写正确
- 点击提交
----
- 打开网站，输入网址
- 正确输入用户名和密码，点击登录
- 在运营管理系统下拉框选择点击权限管理系统
- 点击权限组管理
- 点击添加权限组
- 权限组编码输入好的好的（中文）
- 其他输入框填写正确
- 点击提交

其中只有第六步的操作不一样，可以把重复的内容提取出来，那么就可以看成是一个树型结构
![测试用例步骤Xmind](https://github.com/yyy-charlie/Test-Case-Frame/blob/master/images/测试用例步骤XMind.png)


使用自动生成测试用例框架只需要输入必须的步骤，对于那些重复的关于边界值测试的步骤可以自动生成
![批量新增步骤1](https://github.com/yyy-charlie/Test-Case-Frame/blob/master/images/自动生成测试用例示例1.png)
![批量新增步骤2](https://github.com/yyy-charlie/Test-Case-Frame/blob/master/images/自动生成测试用例示例2.png)
