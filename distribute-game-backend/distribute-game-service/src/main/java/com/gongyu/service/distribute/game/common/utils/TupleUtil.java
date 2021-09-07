package com.gongyu.service.distribute.game.common.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class TupleUtil<A, B> {
	@Data
	public static class TwoTuple<A, B> {
		private A first;
		private B second;

		public TwoTuple(A a, B b) {
			this.first = a;
			this.second = b;
		}
	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	public static class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
		private C third;

		public ThreeTuple(A a, B b, C c) {
			super(a, b);
			this.third = c;
		}
	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	public static class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {
		private D fourth;

		public FourTuple(A a, B b, C c, D d) {
			super(a, b, c);
			this.fourth = d;
		}
	}
}
