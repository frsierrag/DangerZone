package com.grupo5.dangerzone.data;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.grupo5.dangerzone.model.Usuario;

public class Usuarios {

    public static void guardarUsuario(final FirebaseUser user) {
        Usuario usuario = new Usuario(user.getDisplayName(), user.getEmail());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Usuarios").document(user.getUid()).set(usuario);
    }
}
