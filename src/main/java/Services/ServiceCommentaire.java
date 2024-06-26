package Services;

import Data_source.Config;
import Entity.Commentaire;
import Entity.Publication;
import interfaces.IService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ServiceCommentaire implements IService<Commentaire> {
    Connection cnx = Config.getInstance().getCon();

    private final ServicePublication servicePublication = new ServicePublication();
    @Override
    public void ajouter(Commentaire commentaire) {
        try {
            String requete = "INSERT INTO commentaire (description, dateCom, idPub) VALUES (?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, commentaire.getDescription());
            pst.setTimestamp(2, commentaire.getDateCom());

            // Vérifiez si la publication associée n'est pas null avant de définir l'idPub
            Publication pub = commentaire.getPub();
            if (pub != null) {
                pst.setInt(3, pub.getIdPub());
            } else {
                // Gérez le cas où la publication est null
                pst.setNull(3, Types.INTEGER);
            }

            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }



    @Override
    public void supprimer(Commentaire commentaire) {
        try {
            String requete = "DELETE FROM commentaire WHERE idComment = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, commentaire.getIdComment());
            pst.executeUpdate();
            System.out.println("commentaire supprimée !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void modifier(Commentaire commentaire) {
        try {
            String requete = "UPDATE commentaire SET description=?, dateCom=now(), idPub=? WHERE idComment=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, commentaire.getDescription());
            pst.setInt(2, commentaire.getPub().getIdPub());
            pst.setInt(3, commentaire.getIdComment());
            pst.executeUpdate();
            System.out.println("Commentaire modifié !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void updateDescription(int commentId, String newDescription) {
        try {
            String requete = "UPDATE commentaire SET description=? WHERE idComment=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, newDescription);
            pst.setInt(2, commentId);
            pst.executeUpdate();
            System.out.println("Description du commentaire mise à jour !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }




    @Override
    public ObservableList<Commentaire> afficher() {
        ObservableList<Commentaire> commentaires = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM commentaire";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Commentaire commentaire = new Commentaire();
                commentaire.setIdComment(rs.getInt("idComment"));
                commentaire.setDescription(rs.getString("description"));
                commentaire.setDateCom(rs.getTimestamp("dateCom"));
                int idPub = rs.getInt("idPub");
                Publication pub = servicePublication.getPublicationById(idPub); // Utilisation de servicePublication pour obtenir la publication
                commentaire.setPub(pub);
                commentaires.add(commentaire);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return commentaires;
    }


    public ObservableList<Commentaire> getCommentByPub(int idPub) {
        ObservableList<Commentaire> commentaires = FXCollections.observableArrayList();

        try {
            String requete = "SELECT * FROM commentaire WHERE idPub = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, idPub);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Commentaire commentaire = new Commentaire();
                commentaire.setIdComment(rs.getInt("idComment"));
                commentaire.setDescription(rs.getString("description"));
                commentaire.setDateCom(rs.getTimestamp("dateCom"));
                // commentaire.setPub(rs.getInt("idPub")); // Si nécessaire
                commentaires.add(commentaire);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return commentaires;
    }

}