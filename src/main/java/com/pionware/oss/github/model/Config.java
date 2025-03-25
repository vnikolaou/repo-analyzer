package com.pionware.oss.github.model;

public class Config {
    private final String token;
    
    private final String language;
    private final String topic;
    private final int stars;
    
    private Config(Builder builder) {
        this.token = builder.token;
        
        this.language = builder.language;
        this.topic = builder.topic;
        this.stars = builder.stars;
    }

    public String getToken() {
    	return this.token;
    }
    
    public String getLanguage() {
    	return this.language;
    }
    
    public String getTopic() {
    	return this.topic;
    }
    
    public int getStars() {
    	return this.stars;
    }
    
    public static class Builder {
        private String token;
        
        private String language;
        private String topic;
        private int stars;
        
        public Builder() { 

        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }
        
        public Builder setTopic(String topic) {
            this.topic = topic;
            return this;
        }
        
        public Builder setStars(int stars) {
            this.stars = stars;
            return this;
        }
        
        public Config build() {
            return new Config(this);
        }
    }
}
