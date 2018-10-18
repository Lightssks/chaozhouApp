package com.example.chaozhou.api.bean;

public class ContrastId {

    private data data;

    public ContrastId.data getData() {
        return data;
    }

    public void setData(ContrastId.data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ContrastId{" +
                "data=" + data +
                '}';
    }

    public class data {

        private String mid;
        private String contrastId;

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getContrastId() {
            return contrastId;
        }

        public void setContrastId(String contrastId) {
            this.contrastId = contrastId;
        }

        @Override
        public String toString() {
            return "data{" +
                    "mid='" + mid + '\'' +
                    ", contrastId='" + contrastId + '\'' +
                    '}';
        }
    }
}
