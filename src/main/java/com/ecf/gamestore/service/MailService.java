package com.ecf.gamestore.service;

import com.ecf.gamestore.models.*;
import com.ecf.gamestore.utils.CollectionUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class MailService {

    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

    @Value("${gs.test.mail.username}")
    private  String emailForTest;
    @Value("${gs.api.link.reset-password}")
    private String resetPasswordUrl;
    @Value("${GS_SITE_LINK_HOME}")
    private String homeLink;

    private final JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    /**
     *
     * @param order
     * @return boolean
     */
    public boolean sendOrderConfirmationEmail (Order order){
        LOG.debug("## sendOrderConfirmationEmail (Order order)");
        if(Objects.isNull(order)) return false;
        GSUser user = order.getUser();
        List<OrderLine> orderLines = order.getOrderLines();
        if(CollectionUtils.isNullOrEmpty(orderLines)) return false;

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        double totalPrix = 0d;
        double totalReduction = 0d;

        StringBuilder sb = new StringBuilder();
        for(OrderLine orderLine : orderLines) {
            GameArticle article = orderLine.getGameArticle();
            double price = article.getPrice();
            totalPrix += (orderLine.getQuantity() * price);

            sb.append("\n\t\tTitre              : ").append(article.getGameInfo().getTitle()).append("\n");
            sb.append("\t\tVersion            : ").append(article.getPlatform().getLabel()).append("\n");
            sb.append("\t\tQuantité           : ").append(orderLine.getQuantity()).append("\n");
            sb.append("\t\tPrix unitaire      : ").append(numberFormat.format(price)).append("\n");


            if(Objects.nonNull(orderLine.getPromotion())){
                Promotion promotion = orderLine.getPromotion();
                double reduction = (price * promotion.getDiscountRate()) / 100;
                sb.append("\t\tRéduction unitaire : ").append(promotion.getDiscountRate()).append("% --> ")
                        .append(numberFormat.format(reduction)).append("\n");
                totalReduction += orderLine.getQuantity() * reduction;
            }
            sb.append("\t\t---------------").append("\n");
        }

        double sousTotal = totalPrix - totalReduction;

        try {
            String subject = "Confirmation de votre commande - GameStore";
            String text = """
                                    
                     Bonjour %s %s,
                     
                     Nous vous remercions pour votre commande. Celle-ci a bien été prise en compte et sera disponible selon les détails suivants :
                     Détails de la commande :
                     
                         Produits commandés :
                             %s
                         Prix officiel :  %s
                         Réduction     : -%s
                         Sous-total    :  %s
                     
                     Détails de retrait :
                     
                         Agence de retrait : GameStore de %s
                         Date de retrait : %s entre 9h et 19h
                     
                     Nous vous rappelons que votre commande sera disponible pour retrait uniquement à la date indiquée. 
                     N'oubliez pas d'apporter une pièce d'identité lors du retrait.
                     
                     Vous pouvez consulter l'historique de vos commandes dans votre espace personnel à tout moment.
                     
                     Merci encore pour votre confiance.
                     
                     Cordialement,
                     L'équipe GameStore    
                    """.formatted(
                    user.getFirstName(), user.getLastName(),
                    sb.toString(),
                    numberFormat.format(totalPrix),
                    numberFormat.format(totalReduction),
                    numberFormat.format(sousTotal),
                    order.getAgence().getCity(),
                    order.getPickupDate());
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(this.emailForTest);
            helper.setSubject(subject);
            helper.setText(text);

            this.mailSender.send(message);
            return true;
        } catch (MailException | MessagingException e) {
            LOG.error("Failed to send email: " + e.getMessage());
            return false;
        }
    }

    public boolean sendSignupConfirmation(GSUser user) {
        LOG.debug("## sendSignupConfirmation(GSUser user)");
        if(Objects.isNull(user)) return false;

        try {
            String subject = "Confirmation de création de compte GameStore";
            String text = """
                                    
            Bonjour %s,
                                                       
            Nous sommes ravis de vous accueillir sur GameStore !
            
            Votre compte a été créé avec succès. 
            Vous pouvez désormais accéder à votre espace personnel et profiter de nos services. 
            Pour vous connecter, utilisez simplement votre adresse email et le mot de passe que 
            vous avez défini lors de l'inscription.
            
            Aller sur GameStore : %s
                     
            Cordialement,
            L'équipe GameStore    
            """.formatted(
            user.getFirstName(), this.homeLink);
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(this.emailForTest);
            helper.setSubject(subject);
            helper.setText(text);

            this.mailSender.send(message);
            return true;
        } catch (MailException | MessagingException e) {
            LOG.error("Failed to send email: " + e.getMessage());
            return false;
        }

    }

}
