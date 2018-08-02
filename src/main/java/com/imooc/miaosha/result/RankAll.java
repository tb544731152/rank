package com.imooc.miaosha.result;

public class RankAll {
	private int rank;
	private String name;
	private String zyzsId;
	private Double score;
	
	
	public RankAll() {
		super();
	}
	public RankAll(int rank, String name, String zyzsId, Double score) {
		super();
		this.rank = rank;
		this.name = name;
		this.zyzsId = zyzsId;
		this.score = score;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZyzsId() {
		return zyzsId;
	}
	public void setZyzsId(String zyzsId) {
		this.zyzsId = zyzsId;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
}
