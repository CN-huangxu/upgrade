# spring 集成 JWT 权限控制等

## [spring-security 中核心概念](https://blog.csdn.net/qq_36882793/article/details/102839333)

| 类名                       | 概念                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| -------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **AuthenticationManager**  | 用户认证的管理类，所有的认证请求（比如 login）都会通过提交一个 token 给 AuthenticationManager 的 authenticate()方法来实现。当然事情肯定不是它来做，具体校验动作会由 AuthenticationManager 将请求转发给具体的实现类来做。根据实现反馈的结果再调用具体的 Handler 来给用户以反馈。                                                                                                                                                                                                                                                                                                                |
| **AuthenticationProvider** | 认证的具体实现类，一个 provider 是一种认证方式的实现，比如提交的用户名密码我是通过和 DB 中查出的 user 记录做比对实现的，那就有一个 DaoProvider；如果我是通过 CAS 请求单点登录系统实现，那就有一个 CASProvider。按照 Spring 一贯的作风，主流的认证方式它都已经提供了默认实现，比如 DAO、LDAP、CAS、OAuth2 等。前面讲了 AuthenticationManager 只是一个代理接口，真正的认证就是由 AuthenticationProvider 来做的。一个 AuthenticationManager 可以包含多个 Provider，每个 provider 通过实现一个 support 方法来表示自己支持那种 Token 的认证。AuthenticationManager 默认的实现类是 ProviderManager。 |
| **UserDetailService**      | 用户认证通过 Provider 来做，所以 Provider 需要拿到系统已经保存的认证信息，获取用户信息的接口 spring-security 抽象成 UserDetailService。                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| **AuthenticationToken**    | 所有提交给 AuthenticationManager 的认证请求都会被封装成一个 Token 的实现，比如最容易理解的 UsernamePasswordAuthenticationToken。                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| **SecurityContext**        | 当用户通过认证之后，就会为这个用户生成一个唯一的 SecurityContext，里面包含用户的认证信息 Authentication。通过 SecurityContext 我们可以获取到用户的标识 Principle 和授权信息 GrantedAuthrity。在系统的任何地方只要通过 SecurityHolder.getSecruityContext()就可以获取到 SecurityContext。                                                                                                                                                                                                                                                                                                        |

## [Spring Security 的核心拦截器](https://blog.csdn.net/qq_36882793/article/details/102839333)

| 拦截器                                      | 释义                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| ------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **HttpSessionContextIntegrationFilter**     | 位于过滤器顶端，第一个起作用的过滤器。用途一，在执行其他过滤器之前，率先判断用户的 session 中是否已经存在一个 SecurityContext 了。如果存在，就把 SecurityContext 拿出来，放到 SecurityContextHolder 中，供 Spring Security 的其他部分使用。如果不存在，就创建一个 SecurityContext 出来，还是放到 SecurityContextHolder 中，供 Spring Security 的其他部分使用。用途二，在所有过滤器执行完毕后，清空 SecurityContextHolder，因为 SecurityContextHolder 是基于 ThreadLocal 的，如果在操作完成后清空 ThreadLocal，会受到服务器的线程池机制的影响。 |
| **LogoutFilter**                            | 只处理注销请求，默认为/j_spring_security_logout。用途是在用户发送注销请求时，销毁用户 session，清空 SecurityContextHolder，然后重定向到注销成功页面。可以与 rememberMe 之类的机制结合，在注销的同时清空用户 cookie。                                                                                                                                                                                                                                                                                                                           |
| **AuthenticationProcessingFilter**          | 处理 form 登陆的过滤器，与 form 登陆有关的所有操作都是在此进行的。默认情况下只处理/j_spring_security_check 请求，这个请求应该是用户使用 form 登陆后的提交地址此过滤器执行的基本操作时，通过用户名和密码判断用户是否有效，如果登录成功就跳转到成功页面（可能是登陆之前访问的受保护页面，也可能是默认的成功页面），如果登录失败，就跳转到失败页面。                                                                                                                                                                                              |
| **DefaultLoginPageGeneratingFilter**        | 此过滤器用来生成一个默认的登录页面，默认的访问地址为/spring_security_login，这个默认的登录页面虽然支持用户输入用户名，密码，也支持 rememberMe 功能，但是因为太难看了，只能是在演示时做个样子，不可能直接用在实际项目中。                                                                                                                                                                                                                                                                                                                       |
| **BasicProcessingFilter**                   | 此过滤器用于进行 basic 验证，功能与 AuthenticationProcessingFilter 类似，只是验证的方式不同。                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| **SecurityContextHolderAwareRequestFilter** | 此过滤器用来包装客户的请求。目的是在原始请求的基础上，为后续程序提供一些额外的数据。比如 getRemoteUser()时直接返回当前登陆的用户名之类的。                                                                                                                                                                                                                                                                                                                                                                                                     |
| **RememberMeProcessingFilter**              | 此过滤器实现 RememberMe 功能，当用户 cookie 中存在 rememberMe 的标记，此过滤器会根据标记自动实现用户登陆，并创建 SecurityContext，授予对应的权限。                                                                                                                                                                                                                                                                                                                                                                                             |
| **AnonymousProcessingFilter**               | 为了保证操作统一性，当用户没有登陆时，默认为用户分配匿名用户的权限。                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| **ExceptionTranslationFilter**              | 此过滤器的作用是处理中 FilterSecurityInterceptor 抛出的异常，然后将请求重定向到对应页面，或返回对应的响应错误代码                                                                                                                                                                                                                                                                                                                                                                                                                              |
| **SessionFixationProtectionFilter**         | 防御会话伪造攻击。有关防御会话伪造的详细信息                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| **FilterSecurityInterceptor**               | 用户的权限控制都包含在这个过滤器中。功能一：如果用户尚未登陆，则抛出 AuthenticationCredentialsNotFoundException“尚未认证异常”。功能二：如果用户已登录，但是没有访问当前资源的权限，则抛出 AccessDeniedException“拒绝访问异常”。功能三：如果用户已登录，也具有访问当前资源的权限，则放行。我们可以通过配置方式来自定义拦截规则                                                                                                                                                                                                                  |

## Security 框架搭建（文件讲解）

    需要配合项目文件查看

1. UserDetails: 声明一个 Spring Security 的 User 实例，供 Spring Security 使用。

2. UserDetailsService: 供 Spring Security 使用的方法类，会调用 loadUser 获取登录用户

3. AuthenticationFilter: token 有效性验证拦截器

4. JwtUtils: 生成 token, 从 token 中读取信息，验证是否过期

5. SecurityConfig: 配置类

6. AuthenticationEntryPoint: 用户权限不足处理

代码讲解

1. SecurityConfig 中配置 完成 后， 指定请求将被 AuthenticationFilter 拦截
2. 如果 AuthenticationFilter 验证通过 将 authentication 保存在上下文中
3. 用户的请求会到达 authenticated()方法 没得权限的 将被干掉
4. exceptionHandling().authenticationEntryPoint(unauthorizedHandler) 处理错误信息

> 注意
>
> 过滤器的执行顺序是在全局异常机制启动之前执行的，所以一旦过滤器中发生异常，全局异常捕获机制无法使用

## [全局异常处理](https://z.itpub.net/article/detail/07D65E61437F86122657F32D0B98713B)

@RestControllerAdvice，如果用了它，错误处理方法的返回值不会表示用的哪个视图，而是会作为 HTTP body 处理，即相当于错误处理方法加了@ResponseBody 注解。

@ExceptionHandler 标识异常类型对应的处理方法

全局异常处理类

```java
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArguemntNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.OK).body(ResultJson.failure(ResultCode.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    /**
     * 权限不足
     */
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity handleInsufficientAuthenticationException(InsufficientAuthenticationException e,
                                                                    HttpServletRequest request) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登录或该账号没有权限");
    }


    @ExceptionHandler(CommonException.class)
    public ResponseEntity handleCommonException(CommonException e,
                                                HttpServletRequest request) {
        e.printStackTrace();
        System.out.println(e.getCode());
        return ResponseEntity.status(HttpStatus.OK).body(ResultJson.failure(ResultCode.SERVER_ERROR, e.getMessage()));
    }

}
```

自定义异常

```java
public class CommonException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(CommonException.class);
    private static final long serialVersionUID = 5044938065901970022L;
    private final transient Object[] parameters;
    private String code;

    public CommonException(String code, Object... parameters) {
        super(code);
        this.parameters = parameters;
        this.code = code;
    }

    public CommonException(String code, Throwable cause, Object... parameters) {
        super(code, cause);
        this.parameters = parameters;
        this.code = code;
    }

    public CommonException(String code, Throwable cause) {
        super(code, cause);
        this.code = code;
        this.parameters = new Object[0];
    }

    public CommonException(Throwable cause, Object... parameters) {
        super(cause);
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return this.parameters;
    }

    public String getCode() {
        return this.code;
    }

    public String getTrace() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = null;

        label57:
        {
            Object var4;
            try {
                ps = new PrintStream(baos, false, StandardCharsets.UTF_8.name());
                break label57;
            } catch (UnsupportedEncodingException var8) {
                logger.error("Error get trace, unsupported encoding.", var8);
                var4 = null;
            } finally {
                if (ps != null) {
                    ps.close();
                }

            }

            return (String) var4;
        }

        this.printStackTrace(ps);
        ps.flush();
        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new LinkedHashMap();
        map.put("code", this.code);
        map.put("message", super.getMessage());
        return map;
    }
}
```

## [定时任务](https://www.cnblogs.com/mmzs/p/10161936.html)

使用SpringBoot创建定时任务非常简单，目前主要有以下三种创建方式：

1. 静态：基于注解(@Scheduled)
2. 基于接口
3. 基于注解设定多线程定时任务

```java
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class Schedule {
    //3.添加定时任务
    // @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=5000)
    private void configureTasks() {
        log.info("执行静态定时任务时间: " + LocalDateTime.now());
    }
}
```
Cron表达式参数分别表示：

秒（0~59） 例如0/5表示每5秒
分（0~59）
时（0~23）
日（0~31）的某天，需计算
月（0~11）
周几（ 可填1-7 或 SUN/MON/TUE/WED/THU/FRI/SAT）
@Scheduled：除了支持灵活的参数表达式cron之外，还支持简单的延时操作，例如 fixedDelay ，fixedRate 填写相应的毫秒数即可。