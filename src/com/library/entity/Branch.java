package com.library.entity;

public class Branch extends LibraryEntity {

    private String branchName;
    private String address;

    public Branch(String branchName, String address) {
        this.branchName = branchName;
        this.address = address;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Branch{" +
                super.toString() +
                " branchName='" + branchName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
