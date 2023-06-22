package com.codetreatise.view;

import java.util.ResourceBundle;

public enum FxmlView {

    USER {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("user.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/User.fxml";
        }
    }, 
    LOGIN {
        @Override
		public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
		public String getFxmlFile() {
            return "/fxml/Login.fxml";
        }
    },

    SHOP {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("shop.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Tienda.fxml";
        }
    },

    PET {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("pet.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Pet.fxml";
        }
    };



    
    public abstract String getTitle();
    public abstract String getFxmlFile();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
