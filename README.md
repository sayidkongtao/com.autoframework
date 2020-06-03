
## Flutter Allure TestNG Example

### Getting Started

To generate Allure Report you should perform following steps:

```bash
$ ./mvnw clean test

在window系统上，命令为：mvnw clean test
```

There is another way of generating the report. The generated report can be opened here "target/site/allure-maven-plugin/index.html". The command to generate the report is the following:

```bash
$ ./mvnw io.qameta.allure:allure-maven:report

在window系统上，命令为：mvnw io.qameta.allure:allure-maven:report
```

Demo路径： autoframework/demo/TestDemoModule.java
simpleTestThree所需安装包在：app/flutter_native_debug.apk

### More

* [Documentation](https://docs.qameta.io/allure/2.0/)
* [Issue Tracking](https://github.com/allure-framework/allure2/issues?labels=&milestone=&page=1&state=open)
* Gitter chat room: [https://gitter.im/allure-framework/allure-core](https://gitter.im/allure-framework/allure-core)
* StackOverflow tag: [Allure](http://stackoverflow.com/questions/tagged/allure)
