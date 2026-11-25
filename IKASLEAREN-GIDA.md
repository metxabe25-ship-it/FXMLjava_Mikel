# 1. saioa: FXMLren sarrera

## 1. Zer da FXML

FXML **Java objektuak sortu eta konfiguratzeko modu bat da, XMLz idatzita**. Orain arte bista Java klase batean eraiki duzue (`new`, `set...`). FXMLrekin, bista bera fitxategi batean deskribatzen da, eta exekuzioan `FXMLLoader` klaseak irakurri eta objektuak sortzen ditu.

Helburua: **diseinua (zer dago non) eta logika (zer gertatzen da) bereiztea**.

- **Alde onak:** bista irakurterrazagoa da, kodetik bereizita dago, eta editore grafiko batekin (Scene Builder) egin daiteke.
- **Alde txarrak:** FXMLa eta Java izenen bidez lotzen dira, eta izen bat gaizki idaztea **exekuzio-errorea** da, ez konpilaziokoa.

## 2. Javatik FXMLra: lau arau

| Araua | Java hutsean | FXMLan |
|---|---|---|
| **Elementua = objektu bat** | `new TextField()` | `<TextField/>` |
| **Atributua = setter bat** | `button.setText("Gorde")` | `<Button text="Gorde"/>` |
| **Elementu umeak = edukiontziaren umeak** | `root.getChildren().add(label)` | `<GridPane> <Label/> </GridPane>` |
| **Atributu "estatikoak" = edukiontziaren murrizketak** | `GridPane.setRowIndex(label, 2)` | `<Label GridPane.rowIndex="2"/>` |

Bi xehetasun:

- **Balio konplexuak** (adibidez, `Insets` bat) etiketa minuskula batekin idazten dira: `<padding><Insets top="20" .../></padding>`. Hori `root.setPadding(new Insets(...))` da.
- **Inportazioak:** erabilitako klase bakoitzak bere inportazioa behar du: `<?import javafx.scene.control.Button?>`.

Kontuz `GridPane`-rekin: Javako `add(nodoa, zutabea, errenkada)` metodoan zutabea lehenik doa. FXMLan `GridPane.columnIndex` eta `GridPane.rowIndex` dira. Edukiontzi bakoitzak bere atributu estatikoak ditu: ikus 8. atala.

## 3. View/Controller-etik FXMLra

| | Orain arte (Java hutsa) | FXMLrekin |
|---|---|---|
| Bista | Java klase bat | `.fxml` fitxategi bat |
| Nork sortzen du kontroladorea | SceneManager-ak: `new LoginController(...)` | `FXMLLoader`-ek, `fx:controller` irakurrita |
| Kontroladoreak zer jasotzen du | **Balioak**: bistak testu-eremuak irakurri eta kontroladorearen metodo bati pasatzen dizkio | **Nodoak**: `FXMLLoader`-ek `@FXML` eremuak betetzen ditu (injekzioa), eta kontroladoreak irakurtzen ditu balioak |
| Nola lotzen dira gertaerak | Bistak: `setOnAction(...)`, eta kontroladorearen metodo bati deitzen dio | FXMLak: `onAction="#gorde"`, kontroladorearen metodo bati |

Bistak (FXMLak) badaki zein den bere kontroladorea. Kontroladoreak ez du bista sortzen: nodoak jasotzen ditu.

**Kontuz: "kontroladorea" ez da gauza bera bi diseinuetan.**
- *Lehen*, kontroladoreak **balioak** jasotzen zituen (`String`, `int`...), eta ez zekien interfazea nolakoa zen. Logika eta datuak zituen: MVCko kontroladorea. Horregatik zen errazagoa probatzeko, interfazerik gabe.
- *FXMLrekin*, kontroladoreak **nodoak** jasotzen ditu (`TextField`, `Label`...), eta bistaren parte bat bihurtzen da: lehen bistak egiten zuena egiten du (botoiak entzun, testu-eremuak irakurri), eta logika ere bai.

Bereizketa berreskuratzeko, FXMLko kontroladoreak nodoak irakurri eta **balioak** pasatzen dizkio logika-geruza bati (DAOa, zerbitzu bat), lehen bezala:

```java
@FXML
private void gorde() {
    liburuDAO.gorde(izenburuaField.getText(), egileaField.getText());   // balioak, ez nodoak
}
```

## 4. Zer egiten du `FXMLLoader.load()` metodoak

Ordena honetan:

1. FXML fitxategia irakurri eta nodo guztiak sortzen ditu.
2. `fx:controller` badago, kontroladorea sortzen du, **argumenturik gabeko eraikitzailearekin**.
3. `fx:id` bakoitzeko, izen bereko `@FXML` eremua betetzen du.
4. `onAction="#gorde"` bezalako atributuak kontroladoreko metodoei lotzen dizkie.
5. Kontroladoreak `initialize()` metodoa badu, orduan deitzen dio.
6. Erro-nodoa itzultzen du.

Ondorioa: **eraikitzailean `@FXML` eremuak oraindik `null` dira**. Hasierako konfigurazioa `initialize()`-n egiten da.

## 5. Lotura-arauak

- **`fx:controller="pakete.osoa.Klasea"`**: erro-elementuan bakarrik. FXML bakoitzak kontroladore bakarra du.
- **`fx:id="izenburuaField"`** ↔ **`@FXML private TextField izenburuaField;`**: izenak berdin-berdinak (maiuskulak barne), eta mota bateragarria.
- **`@FXML`** behar da eremua edo metodoa `private` bada. Beti `private` + `@FXML` erabiliko dugu.
- **`onAction="#gorde"`**: `#` ikurrak "kontroladoreko metodoa" esan nahi du. Metodoak ez du argumenturik, edo `ActionEvent` bat jasotzen du.
- **`initialize()`**: aukerakoa, argumenturik gabe, eta `@FXML`-ekin `private` bada.

## 6. FXMLa kargatzea

```java
FXMLLoader loader = new FXMLLoader(App.class.getResource("liburu-form.fxml"));
Parent root = loader.load();
```

**Non bilatzen du Javak fitxategia.** Konpilatzean, Mavenek `src/main/java`-ko klaseak eta `src/main/resources`-eko fitxategiak karpeta berean elkartzen ditu: `target/classes`. Han, `App.class` eta `liburu-form.fxml` karpeta berean geratzen dira, biak `eus/liburutegia/` bidean daudelako. Horregatik errepikatzen da pakete-egitura `resources`-en.

| Kodea | Bilatzen duen fitxategia (`target/classes`-en) |
|---|---|
| `App.class.getResource("liburu-form.fxml")` | `eus/liburutegia/liburu-form.fxml` (erlatiboa: `App`-en paketean) |
| `App.class.getResource("bistak/login.fxml")` | `eus/liburutegia/bistak/login.fxml` (erlatiboa, azpikarpeta batekin) |
| `App.class.getResource("/eus/liburutegia/liburu-form.fxml")` | `eus/liburutegia/liburu-form.fxml` (absolutua: errotik) |
| `LiburuFormController.class.getResource("liburu-form.fxml")` | `eus/liburutegia/controller/liburu-form.fxml` → **ez dago**: `null` |

- **Erlatiboa** (`/` gabe): erabilitako **klasearen paketetik** abiatzen da. Klasea aldatuz gero, bidea ere aldatzen da.
- **Absolutua** (`/` hasieran): beti errotik. Ez dio axola zein klase erabiltzen den.
- Fitxategia aurkitzen ez bada, `getResource()`-ek `null` itzultzen du, eta errorea geroago agertzen da, `load()` deitzean: `Location is not set`.

## 7. `module-info.java`: `opens`

`FXMLLoader`-ek isla (*reflection*) erabiltzen du eremu eta metodo pribatuetara sartzeko. Moduluek hori debekatzen dute, paketea irekitzen ez bada:

```java
opens eus.liburutegia.controller to javafx.fxml;
```

Lerro hori paketea sortu ondoren gehitu: pakete hutsa irekitzeak konpilazio-errorea ematen du.

## 8. Layout-ak FXMLan (cheatsheet)

**Bi arau orokor:**
- Edukiontzi guztiek onartzen dute `padding` (barruko marjina): `<padding><Insets top="10" right="10" bottom="10" left="10"/></padding>`.
- `Edukiontzia.propietatea` atributuak (adibidez, `GridPane.rowIndex`) **umean** idazten dira, eta gurasoak bakarrik erabiltzen ditu. Gurasoa beste edukiontzi bat bada, ez du errorerik ematen, baina ez du ezer egiten.

### GridPane: errenkadak eta zutabeak

Formularioetarako. Umeak `children`-en doaz zuzenean, eta bakoitzak bere gelaxka adierazten du.

| Non | Atributua | Zertarako |
|---|---|---|
| GridPane-n | `hgap`, `vgap` | Zutabeen eta errenkaden arteko tartea |
| Umean | `GridPane.rowIndex`, `GridPane.columnIndex` | Gelaxka (0tik hasita; ez badago, 0) |
| Umean | `GridPane.columnSpan`, `GridPane.rowSpan` | Zenbat gelaxka hartzen dituen |
| Umean | `GridPane.halignment` (`LEFT`, `CENTER`, `RIGHT`) | Lerrokatzea gelaxkan |

```xml
<GridPane hgap="10" vgap="10">
    <Label text="Izenburua:" GridPane.rowIndex="0" GridPane.columnIndex="0"/>
    <TextField GridPane.rowIndex="0" GridPane.columnIndex="1"/>
    <Label fx:id="mezuaLabel" GridPane.rowIndex="1" GridPane.columnIndex="0" GridPane.columnSpan="2"/>
</GridPane>
```

### BorderPane: bost gune

Leihoaren egitura orokorrerako. Posizioa **ez da atributu bat**: umea gunearen elementuaren barruan sartzen da (`<top>`, `<bottom>`, `<left>`, `<right>`, `<center>`, minuskulaz). Java: `setTop(nodoa)`, `setCenter(nodoa)`...

**Gune bakoitzak nodo bakarra** hartzen du. Gauza bat baino gehiago jartzeko, bildu HBox, VBox edo GridPane batean.

| Non | Atributua | Zertarako |
|---|---|---|
| Umean | `BorderPane.margin` | Marjina bere gunean |
| Umean | `BorderPane.alignment` (`CENTER`, `TOP_LEFT`...) | Lerrokatzea bere gunean |

```xml
<BorderPane>
    <top>
        <Label text="Liburuak bilatu">
            <BorderPane.margin><Insets bottom="15"/></BorderPane.margin>
        </Label>
    </top>
    <center>
        <GridPane> ... </GridPane>
    </center>
    <bottom>
        <HBox> ... </HBox>
    </bottom>
</BorderPane>
```

### HBox eta VBox: lerro batean

HBox: ezkerretik eskuinera. VBox: goitik behera. Umeak `children`-en doaz zuzenean, ordenan.

| Non | Atributua | Zertarako |
|---|---|---|
| HBox/VBox-en | `spacing` | Umeen arteko tartea |
| HBox/VBox-en | `alignment` (`CENTER`, `CENTER_LEFT`, `CENTER_RIGHT`...) | Umeen lerrokatzea |
| Umean | `HBox.hgrow` / `VBox.vgrow` (`ALWAYS`, `SOMETIMES`, `NEVER`) | Hazi egin daitekeen, lekua betetzeko |
| Umean | `HBox.margin` / `VBox.margin` | Umearen marjina |

```xml
<HBox spacing="10" alignment="CENTER_RIGHT">
    <TextField HBox.hgrow="ALWAYS"/>   <!-- gainerako lekua betetzen du -->
    <Button text="Bilatu"/>
</HBox>
```

### Konbinatzea

Leiho erreal bat edukiontziak bata bestearen barruan jarriz egiten da. Adibidez:

```
BorderPane
├── top     → Label (izenburua)
├── center  → GridPane (Formularioa)
│               └── HBox (Label, TextField), HBox (RadioButton-ak)...
└── bottom  → VBox
                ├── HBox (botoiak, eskuinean)
                └── Label (mezua)
```

## 9. Ohiko akatsak

| Sintoma | Kausa |
|---|---|
| `IllegalStateException: Location is not set` | FXMLaren bidea okerra, edo fitxategia ez dago `resources`-en pakete egokian |
| `LoadException: TextField is not a valid type` | `<?import?>` falta da |
| `NullPointerException` kontroladorean | `fx:id` eta eremuaren izena ez datoz bat, `@FXML` falta da, edo eremua eraikitzailean erabili da |
| `LoadException: Error resolving onAction='#gorde'` | Metodoaren izena ez dator bat, edo `@FXML` falta da |
| `IllegalAccessException` | `opens` falta da `module-info.java`-n |
| `ClassNotFoundException` | `fx:controller`-eko izena ez da osoa edo gaizki idatzita dago |

Errore bat agertzen denean, irakurri mezua osorik, `Caused by:` lerroraino. Gehienetan, izen bat ez dator bat.
