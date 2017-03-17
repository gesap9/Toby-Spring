# Toby-Spring
토비의 스프링 소스 실습

Gradle dependency
dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.11'
    compile("mysql:mysql-connector-java:5.1.31")
    compile 'org.springframework:spring-jdbc:3.1.2.RELEASE'
    compile("org.springframework.boot:spring-boot-starter-web:1.4.3.RELEASE")
    compile 'org.springframework:spring-context:3.1.2.RELEASE'
    compile 'junit:junit:4.7'
    compile 'org.springframework:spring-test:3.1.2.RELEASE'
    compile group: 'javax.mail', name: 'mail', version: '1.4.7'
    compile group: 'org.springframework', name: 'spring-context-support', version: '3.1.2.RELEASE'
    compile group: 'org.mockito', name: 'mockito-all', version: '1.10.19'
    compile group:'org.aspectj', name:'aspectjrt', version:'1.7.3'
    compile group:'org.aspectj', name:'aspectjweaver', version:'1.7.3'
    compile group: 'org.springframework', name: 'spring-oxm', version: '3.1.2.RELEASE'
    compile group: 'org.codehaus.castor', name: 'castor', version: '1.2'
    
    compile group: 'org.hsqldb', name: 'hsqldb', version: '2.3.4'
    
}
