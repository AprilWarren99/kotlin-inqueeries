package org.example

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv

class EnvHandler(){
    fun getEnv(): Dotenv {
        return dotenv {
            directory = "app/src/main/resources/"
        }
    }
}