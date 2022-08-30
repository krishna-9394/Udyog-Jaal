package com.example.udyogjaal.utilities;

public class UserType {
    private Boolean provider, selection, seeker;
    public UserType() {
    }
    public UserType(Boolean provider, Boolean seeker, Boolean selection) {
        this.selection = selection;
        this.provider = provider;
        this.seeker = seeker;
    }
    public Boolean getSelection() {
        return selection;
    }

    public void setSelection(Boolean selection) {
        this.selection = selection;
    }

    public Boolean getProvider() {
        return provider;
    }

    public void setProvider(Boolean provider) {
        this.provider = provider;
    }

    public Boolean getSeeker() {
        return seeker;
    }

    public void setSeeker(Boolean seeker) {
        this.seeker = seeker;
    }
}
