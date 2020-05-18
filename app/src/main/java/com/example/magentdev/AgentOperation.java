package com.example.magentdev;

import java.io.Serializable;

public class AgentOperation implements Serializable {
    private String clientAccGID;
    private String transactionCurr;
    private String operationType;
    private String operationName;
    private String clientAccName;
    private int tid;
    private int sid;



    public AgentOperation(String clientAccGID, String operationType, String operationName, String clientAccName, String transactionCurr, int sid) {
        this.clientAccGID = clientAccGID;
        this.operationType = operationType;
        this.operationName = operationName;
        this.clientAccName = clientAccName;
        this.transactionCurr = transactionCurr;
        this.sid = sid;
    }

    public String getClientAccGID() {
        return clientAccGID;
    }

    public void setClientAccGID(String clientAcc) {
        this.clientAccGID = clientAcc;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getClientAccName() {
        return clientAccName;
    }

    public void setClientAccName(String clientAccName) {
        this.clientAccName = clientAccName;
    }

    public String getTransactionCurr() {
        return transactionCurr;
    }

    public void setTransactionCurr(String transactionCurr) {
        this.transactionCurr = transactionCurr;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

}
