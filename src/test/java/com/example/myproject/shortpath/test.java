package com.example.myproject.shortpath;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Sunyh on 2016/10/6.
 */
public class test {

	public static Timestamp start;

	public static void main(String[] args) {

		start = new Timestamp(System.currentTimeMillis());

		int size = 2000;
		LinkedList[] list = completeGraph(2000);
		// printLinkedList(list);
		/*
		 * for (int i = 0; i < size; i++) { Dijkstra(list, i); }
		 */
		Floyd(list);

		Timestamp end = new Timestamp(System.currentTimeMillis());
		System.out.println();
		System.out.println("Floyd graph: complete; size:" + size + "; time use: "
				+ (end.getTime() - start.getTime()) / (1000) + "s");

	}

	public static LinkedList[] drawGraph(int size) {

		LinkedList[] list = new LinkedList[size];
		for (int i = 0; i < size; i++) {
			list[i] = new LinkedList<Integer>();
		}
		Scanner sca = new Scanner(System.in);
		int i = 0;

		while (i < size) {

			String temp = sca.nextLine();
			if (temp.equals("")) {
				i++;
			} else {
				list[i].add(Integer.parseInt(temp));
			}
		}
		return list;
	}

	public static void printLinkedList(LinkedList[] array) {
		int n = array.length;

		for (int i = 0; i < n; i++) {
			System.out.println(array[i]);
		}
	}

	public static int randomGenerator() {
		return (int) (Math.random() * 10) + 1;
	}

	public static LinkedList[] completeGraph(int size) {

		LinkedList[] array = new LinkedList[size];
		for (int i = 0; i < size; i++) {
			array[i] = new LinkedList<Integer>();
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j <= i; j++) {
				if (i == j) {
					array[i].add(0);
				} else {
					array[i].add(randomGenerator());
				}
			}
		}
		return array;
	}

	public static LinkedList[] sparseGraph(int size) {

		LinkedList[] array = new LinkedList[size];
		for (int i = 0; i < size; i++) {
			array[i] = new LinkedList<Integer>();
		}
		int[] index = new int[size];
		int count = size - 1;

		for (int i = 0; i < size; i++) {
			index[i] = (int) (Math.random() * (i));

		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j <= i; j++) {
				if (i == j) {
					array[i].add(0);
				} else {
					if (index[i] == j) {
						array[i].add(randomGenerator());
					} else {
						array[i].add(-1);
					}
				}
			}
		}
		return array;
	}

	public static void Floyd(LinkedList[] array) {

		int size = array.length;
		List[] path = new ArrayList[size * size];

		int u = 0;
		for (int i = 0; i < path.length; i++) {
			path[i] = new ArrayList();
		}

		for (int k = 0; k < size; k++) {
			Timestamp temp = new Timestamp(System.currentTimeMillis());
			long timeleft = (temp.getTime() - start.getTime()) / ((60) * (1000));
			System.out.println(timeleft);
			timeleft = timeleft * (size - k - 1) / (k + 1);
			if (timeleft == 0) {

				System.out.println(k + "/" + size + "       estimated time left :" + " unknown");
			} else {
				System.out.println(k + "/" + size + "       estimated time left :" + timeleft + " min");
			}

			for (int i = 0; i < size; i++) {
				for (int j = 0; j <= i; j++) {
					if (i >= k) {
						if (k >= j) {
							if ((Integer.parseInt(array[i].get(j).toString()) == -1 || Integer
									.parseInt(array[i].get(j).toString()) > Integer.parseInt(array[i].get(k).toString())
											+ Integer.parseInt(array[k].get(j).toString()))
									&& (Integer.parseInt(array[i].get(k).toString()) != -1
											&& Integer.parseInt(array[k].get(j).toString()) != -1)) {

								int value = Integer.parseInt(array[i].get(k).toString())
										+ Integer.parseInt(array[k].get(j).toString());
								array[i].remove(j);
								array[i].add(j, value);
								// i,j = ik + kj

								// path i j = path j k + value(k) + path k j
								path[i * (i + 1) / 2 + j].clear();
								path[i * (i + 1) / 2 + j].addAll(path[i * (i + 1) / 2 + k]);
								path[i * (i + 1) / 2 + j].add(k);
								path[i * (i + 1) / 2 + j].addAll(path[k * (k + 1) / 2 + j]);

							}

						} else {
							if ((Integer.parseInt(array[i].get(j).toString()) == -1 || Integer
									.parseInt(array[i].get(j).toString()) > Integer.parseInt(array[i].get(k).toString())
											+ Integer.parseInt(array[j].get(k).toString()))
									&& (Integer.parseInt(array[i].get(k).toString()) != -1
											&& Integer.parseInt(array[j].get(k).toString()) != -1)) {

								int value = Integer.parseInt(array[i].get(k).toString())
										+ Integer.parseInt(array[j].get(k).toString());
								array[i].remove(j);
								array[i].add(j, value);

								// path i j = path j k + value(k) + reverse(path
								// j k)
								path[i * (i + 1) / 2 + j].clear();
								path[i * (i + 1) / 2 + j].addAll(path[i * (i + 1) / 2 + k]);
								List tempList = path[j * (j + 1) / 2 + k];
								Collections.reverse(tempList);
								path[i * (i + 1) / 2 + j].add(k);
								path[i * (i + 1) / 2 + j].addAll(tempList);

							}
						}
					} else {

						if (k >= j) {
							if ((Integer.parseInt(array[i].get(j).toString()) == -1 || Integer
									.parseInt(array[i].get(j).toString()) > Integer.parseInt(array[k].get(i).toString())
											+ Integer.parseInt(array[k].get(j).toString()))
									&& (Integer.parseInt(array[k].get(i).toString()) != -1
											&& Integer.parseInt(array[k].get(j).toString()) != -1)) {

								int value = Integer.parseInt(array[k].get(i).toString())
										+ Integer.parseInt(array[k].get(j).toString());
								array[i].remove(j);
								array[i].add(j, value);
								path[i * (i + 1) / 2 + j].clear();
								// i j = k i + k j
								List tempList = path[k * (k + 1) / 2 + i];
								Collections.reverse(tempList);
								path[i * (i + 1) / 2 + j].addAll(tempList);

								List tempList2 = path[k * (k + 1) / 2 + j];
								Collections.reverse(tempList2);
								path[i * (i + 1) / 2 + j].add(k);
								path[i * (i + 1) / 2 + j].addAll(tempList2);

							}

						} else {
							if ((Integer.parseInt(array[i].get(j).toString()) == -1 || Integer
									.parseInt(array[i].get(j).toString()) > Integer.parseInt(array[k].get(i).toString())
											+ Integer.parseInt(array[j].get(k).toString()))
									&& (Integer.parseInt(array[k].get(i).toString()) != -1
											&& Integer.parseInt(array[j].get(k).toString()) != -1)) {

								int value = Integer.parseInt(array[k].get(i).toString())
										+ Integer.parseInt(array[j].get(k).toString());
								array[i].remove(j);
								array[i].add(j, value);

								path[i * (i + 1) / 2 + j].clear();
								List tempList = path[j * (j + 1) / 2 + k];
								Collections.reverse(tempList);
								path[i * (i + 1) / 2 + j].addAll(path[k * (k + 1) / 2 + i]);
								path[i * (i + 1) / 2 + j].add(k);
								path[i * (i + 1) / 2 + j].addAll(tempList);

							}
						}
					}
				}
			}

		}

		// output weight
		/*
		 * for (int i = 0; i < size; i++) { for (int j = 0; j < size; j++) {
		 * if(i >= j){
		 * 
		 * System.out.print(i + "," + j + " : " + path[i*(i+1)/2 + j]);
		 * System.out.println(" weight : " + array[i].get(j)); }else { List temp
		 * = path[j*(j+1)/2 + i]; Collections.reverse(temp); System.out.print(i
		 * + "," + j + " : " + temp); System.out.println(" weight : " +
		 * array[j].get(i)); } } System.out.println(); }
		 */
	}

	public static void Dijkstra(LinkedList[] array, int startNode) {

		int size = array.length;
		int[] dis = new int[size];
		int[] book = new int[size];
		int min = Integer.MAX_VALUE;
		int u = 0;
		String[] result = new String[size];

		for (int i = 0; i < size; i++) {
			if (startNode > i) {
				if (Integer.parseInt(array[startNode].get(i).toString()) == -1) {
					dis[i] = Integer.MAX_VALUE;
				} else {
					dis[i] = Integer.parseInt(array[startNode].get(i).toString());
				}
			} else {
				if (Integer.parseInt(array[i].get(startNode).toString()) == -1) {
					dis[i] = Integer.MAX_VALUE;
				} else {
					dis[i] = Integer.parseInt(array[i].get(startNode).toString());
				}
			}
		}

		for (int i = 0; i < size; i++) {
			book[i] = 0;
			result[i] = "->" + Integer.toString(i);
		}
		book[startNode] = 1;

		for (int i = 0; i < size; i++) {
			// 找到离1号顶点最近的顶点
			min = Integer.MAX_VALUE;
			for (int j = 0; j < size; j++) {
				if (book[j] == 0 && dis[j] < min) {
					min = dis[j];
					u = j;
				}
			}
			book[u] = 1;
			for (int v = 0; v < size; v++) {
				if (u == v)
					continue;
				if (u < v) {
					if (Integer.parseInt(array[v].get(u).toString()) != -1
							&& dis[v] > dis[u] + Integer.parseInt(array[v].get(u).toString())) {

						dis[v] = dis[u] + Integer.parseInt(array[v].get(u).toString());
						int tempSize = result[u].length();
						if (tempSize > 1) {
							result[v] = result[u] + "->" + Integer.toString(v);
						} else {
							result[v] = "->" + Integer.toString(u) + "->" + Integer.toString(v);
						}
					}

				} else {
					if (Integer.parseInt(array[u].get(v).toString()) != -1
							&& dis[v] > dis[u] + Integer.parseInt(array[u].get(v).toString())) {

						dis[v] = dis[u] + Integer.parseInt(array[u].get(v).toString());
						int tempSize = result[u].length();
						if (tempSize > 1) {
							result[v] = result[u] + "->" + Integer.toString(v);
						} else {
							result[v] = "->" + Integer.toString(u) + "->" + Integer.toString(v);
						}
					}
				}
			}
		}

		// out put path
		for (int i = 0; i < result.length; i++) {
			System.out.print("from node" + startNode + " to node" + i + " : ");
			System.out.println(result[i] + " Weight:" + dis[i]);
		}
	}
}
