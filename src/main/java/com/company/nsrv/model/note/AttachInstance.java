package com.company.nsrv.model.note;


public class AttachInstance {
    private final byte[] bytes;

    public AttachInstance(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
