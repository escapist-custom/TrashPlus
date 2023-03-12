package com.samsung.service;

import java.io.IOException;

public interface InformationService {
    String getUserInfo() throws IOException;

    String getAdminInfo() throws IOException;

}
