package com.company.utilities;

import java.io.Serializable;

public enum Response implements Serializable {
    OK,
    REG_ERROR,
    AUTH_ERROR,
    AUTH,
    COMMAND,
    ADD_COMMAND,
}
