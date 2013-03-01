package wiyarmir.scopes.util;

import java.util.ArrayDeque;

public class ScopeMemory extends ArrayDeque<Integer> {

	public ScopeMemory(int hRes) {
		super(hRes);
		for (int i = 0; i < hRes; i++) {
			super.add(0);
		}
	}

	@Override
	public boolean add(Integer e) {
		removeFirst();
		addLast(e);
		return true;
	}

}
