# 1. saioa: bista bat FXMLra eramatea

**Helburua:** Java hutsean eraikitako bista bat FXML bidez idaztea eta kontroladore batekin lotzea.

## Urratsak

1. **(5')** Exekutatu proiektua (`mvn javafx:run` edo IntelliJen `App` klasea). Aztertu `view/LiburuFormView.java`: zer nodo sortzen dira, nola kokatzen dira eta zer egiten du botoiak.
2. **(20')** Osatu `src/main/resources/eus/liburutegia/liburu-form.fxml`, **eskuz**, `LiburuFormView`-en bista bera lortzeko. Ez erabili Scene Builder.
3. **(10')** Sortu `eus.liburutegia.controller.LiburuFormController` klasea:
   - Behar dituzun eremuak `@FXML` anotazioarekin.
   - `gorde()` metodoa: "Gorde" sakatzean, mezua erakutsi behean dagoen etiketan.
   - Lotu FXMLarekin (`fx:controller`, `fx:id`, `onAction`).
   - Kendu iruzkina `module-info.java`-ko `opens` lerroari.
4. **(10')** Aldatu `App` klasea bista FXMLtik kargatzeko (`FXMLLoader`).

**Azkarrentzat:** urtea zenbakia ez bada, erakutsi errore-mezua etiketan.

## Akatsen bat agertzen bada

Irakurri salbuespenaren mezua osorik, `Caused by:` lerroraino. Gehienetan izen bat ez dator bat (fitxategia, `fx:id` edo metodoa).
