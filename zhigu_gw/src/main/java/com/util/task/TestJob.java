package com.util.task;

import java.util.Calendar;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
/**
 * 测试用例
 * @author zhangyonwei
 *
 */
public class TestJob implements InitializingBean {
	@Autowired
	private DynamicSchedulingConfigurer defaultSchedulingConfigurer;

	public void afterPropertiesSet() throws Exception {
		new Thread() {
			public void run() {

				try {
					// 等待任务调度初始化完成
					while (!defaultSchedulingConfigurer.inited()) {
						Thread.sleep(100);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("任务调度初始化完成，添加任务");
				defaultSchedulingConfigurer.addTriggerTask("task", new TriggerTask(new Runnable() {

					@Override
					public void run() {
						System.out.println("run job..." + Calendar.getInstance().get(Calendar.SECOND));

					}
				}, new CronTrigger("0/5 * * * * ? ")));
			};
		}.start();
		new Thread() {
			public void run() {

				try {
					Thread.sleep(30000);
				} catch (Exception e) {
				}
				System.out.println("重置任务............");
				defaultSchedulingConfigurer.resetTriggerTask("task", new TriggerTask(new Runnable() {

					@Override
					public void run() {
						System.out.println("run job..." + Calendar.getInstance().get(Calendar.SECOND));

					}
				}, new CronTrigger("0/10 * * * * ? ")));
			};
		}.start();
	}
	
	public static void main(String[] args) {
		try {
			new TestJob().afterPropertiesSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}