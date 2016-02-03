/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.executarsistema.utils;

import java.net.URL;

public class Browser {

    public static URL INSTANCE = null;

    public void setUrl(String url) {
        try {
            Browser.INSTANCE = new URL(url);
        } catch (Exception e) {

        }
    }
}
