
package com.vee.shang.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * ProjectName: youzan-weixin-event-plugin <br/>
 * ClassName: ThreadPoolConfig <br/>
 * Date: 2016年12月21日 上午11:45:02 <br/>
 * 
 * @author chababa
 * @version
 * @since JDK 1.7
 * @see
 */
@Configuration
@ConfigurationProperties(prefix = "spring.threadpool")
public class ThreadPoolConfig {
	
	  //#线程池维护线程的最少数量
      private int corePoolSize = 5;
      //#线程池维护线程所允许的空闲时间
      private int keepAliveSeconds= 30000;
      //#线程池维护线程的最大数量
      private int maxPoolSize=1000;
      //#线程池所使用的缓冲队列
      private int queueCapacity=200;

	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		// ==========================================
		// 	二、利用线程池启动线程
		// 	Thread udpThread = new Thread(udp);
		// 	poolTaskExecutor.execute(udpThread);
		// 	获取当前线程池活动的线程数：
		// 	int count = poolTaskExecutor.getActiveCount();
		// 	logger.debug("[x] - now threadpool active threads totalNum : " +count);
		// 	ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
		// 	线程池所使用的缓冲队列
		// ==========================================
		// ==========================================
		//		三、配置解释
		//		当一个任务通过execute(Runnable)方法欲添加到线程池时：
		//		1、 如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
		//		2、 如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
		//		3、如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。
		//		4、 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程 maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
		//		5、 当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
		// ==========================================
		ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
		// 线程池所使用的缓冲队列
		poolTaskExecutor.setQueueCapacity(queueCapacity);
		// 线程池维护线程的最少数量
		poolTaskExecutor.setCorePoolSize(corePoolSize);
		// 线程池维护线程的最大数量
		poolTaskExecutor.setMaxPoolSize(maxPoolSize);
		// 线程池维护线程所允许的空闲时间
		poolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
		poolTaskExecutor.initialize();

		return poolTaskExecutor;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public void setKeepAliveSeconds(int keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}
	
	
}
