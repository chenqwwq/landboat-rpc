package com.wastedrivinggroup.listener;

/**
 * @author chen
 * @date 2021/7/18
 **/
public interface Listener {

	/**
	 * 返回监听器的优先级
	 *
	 * @return 监听器的优先级
	 */
	default int getPrior() {
		return ListenerPrior.DEFAULT;
	}

	class ListenerPrior {
		static int HIGHEST = 0;
		static int DEFAULT = 5;
		static int LOWEST = 10;

		static int valueOf(int prior) {
			if (prior <= HIGHEST) {
				return HIGHEST;
			}
			return Math.min(prior, LOWEST);
		}
	}
}
