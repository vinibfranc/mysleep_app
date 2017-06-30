package br.edu.ufcspa.snorlax_angelo.constants;

/**
 * Created by Arun on 06-09-2015.
 *
 * Classe que armazena constantes que seão salvar no @{@link br.edu.ufcspa.snorlax_angelo.model.UserModel}
 */
public class AppConstants {

    public enum SharedPreferenceKeys {
        USER_NAME("userName"),
        USER_EMAIL("userEmail"),
        USER_IMAGE_URL("userImageUrl");


        private String value;

        SharedPreferenceKeys(String value) {
            this.value = value;
        }

        public String getKey() {
            return value;
        }
    }


    // old key public static final String GOOGLE_CLIENT_ID = "571633894749-pgbjhk84qn8vi42d2v16gimlhg3d407a.apps.googleusercontent.com";
    public static final String GOOGLE_CLIENT_ID = "534530719667-ecfe9gtk58u5v0m1j222idbh0qd53vk0.apps.googleusercontent.com";

}
