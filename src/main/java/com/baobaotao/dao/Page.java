package com.baobaotao.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ��ҳ����. ������ǰҳ���ݼ���ҳ��Ϣ���ܼ�¼��.
 *
 */
public class Page implements Serializable {

	private static int DEFAULT_PAGE_SIZE = 20;

	private int pageSize = DEFAULT_PAGE_SIZE; // ÿҳ�ļ�¼��

	private long start; // ��ǰҳ��һ��������List�е�λ��,��0��ʼ

	private List data; // ��ǰҳ�д�ŵļ�¼,����һ��ΪList

	private long totalCount; // �ܼ�¼��

	/**
	 * ���췽����ֻ�����ҳ.
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
	}

	/**
	 * Ĭ�Ϲ��췽��.
	 *
	 * @param start	 ��ҳ���������ݿ��е���ʼλ��
	 * @param totalSize ���ݿ����ܼ�¼����
	 * @param pageSize  ��ҳ����
	 * @param data	  ��ҳ����������
	 */
	public Page(long start, long totalSize, int pageSize, List data) {
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.data = data;
	}

	/**
	 * ȡ�ܼ�¼��.
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * ȡ��ҳ��.
	 */
	public long getTotalPageCount() {
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}

	/**
	 * ȡÿҳ��������.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * ȡ��ǰҳ�еļ�¼.
	 */
	public List getResult() {
		return data;
	}

	/**
	 * ȡ��ҳ��ǰҳ��,ҳ���1��ʼ.
	 */
	public long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * ��ҳ�Ƿ�����һҳ.
	 */
	public boolean isHasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * ��ҳ�Ƿ�����һҳ.
	 */
	public boolean isHasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * ��ȡ��һҳ��һ�����������ݼ���λ�ã�ÿҳ����ʹ��Ĭ��ֵ.
	 *
	 * @see #getStartOfPage(int,int)
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * ��ȡ��һҳ��һ�����������ݼ���λ��.
	 *
	 * @param pageNo   ��1��ʼ��ҳ��
	 * @param pageSize ÿҳ��¼����
	 * @return ��ҳ��һ������
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
}