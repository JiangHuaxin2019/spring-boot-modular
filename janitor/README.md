# janitor-spring-boot-starter
一个可以通过注解添加拦截路径和排除路径的拦截器

1.0 Spring boot配置文件

	#扫描哪些包下的注解
	spring.janitor.packages=com.xxx.xxx,com.xxx.xxx.xxx 
	#手动配置拦截路径
	spring.janitor.path.interceptor=/xxx/**,/xx/*
	#手动配置排除路径
	spring.janitor.path.exclude=/xx/**,/xx/*

2.0 配置拦截器处理器

	@Component
	public class JanitorProcessor implements InterceptorProcessor{

		@Override
		public boolean processor(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			return true;
		}

	}

3.0 注解的使用

    3.1 @JanitorBannister
    
        可以使用类或者方法上，在类上则会生成当前类下所有方法的拦截,且不使用通配符.
	当时你是将value值设置为true时就会使用通配符且会在类上有效.在方法上使用是只会生成当前方法的拦截路径.
        
    3.2 @JanitorToken
    
        可以使用类或者方法上，在类上则会生成当前类下所有方法的拦截,且不使用通配符.
	当时你是将value值设置为true时就会使用通配符且会在类上有效.在方法上使用是只会生成当前方法的拦截路径.
        
    3.3 注意事项
    
        因为排除路径高于拦截路径，所以你可以在@JanitorBannister修饰的类中使用@JanitorToken修饰其中的方法,
	但是不能在@JanitorToken使用@JanitorBannister修饰其中的方法
        
    3.4 示例代码
    
    @Controller
    @JanitorBannister
    public class DefaultController {

        @RequestMapping("test1")
        @ResponseBody
        @JanitorToken
        public String test1() {
            return "test1";
        }

        @RequestMapping("test2")
        @ResponseBody
        public String test2() {
            return "test2";
        }
    }
    
4.0 取消静态资源拦截配置

	#修改今静态资源的访问路径,可自行配置对应路径
	spring.mvc.static-path-pattern=/static/**
	#将静态支援访问路径排除
	spring.janitor.path.exclude=/xx/**,/xx/*,/static/**
	#如果你其他页面的访问路径不要设置在静态资源的排除路径下

