CardView
========

Credit card view for android apps
(based on https://github.com/jessepollak/card)

Everything is created with pure Java code - no images required.

<img src="https://raw.githubusercontent.com/julioz/CardView/master/cardview.gif"/>

Minimum API required: API 10 (Gingerbread 2.3.3)

### How to use
To use CardView in your project, you just need to add this project as a library project and make sure you add the fonts (included in the project assets folder) to your assets folder.

You are able to set the cardholder name, the card number, expiration date and the CVV on the back of the card.

Using Java:
``` java
CardView card = (CardView) rootView.findViewById(R.id.cardview);
card.setCardNumber("3744 4444 4444 4444");
card.setCardName("Uncle Scrooge");
card.setCardValidThru(8, 2015);
card.setCardCvv(432);
card.setValidThruText("valid", "thru");
card.setMonthYearText("MONTH/YEAR");
card.setInformationText("Legal - and usually boring - text");
```

or in XML:
``` xml
<br.com.zynger.cardview.CardView
    android:id="@+id/cardview"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:cardNumber="6011 4444 4444 4444"
    app:cardholderName="uncle scrooge"
    app:cardValidThru="08/2015"
    app:cardValidThruWordOneText="valid"
    app:cardValidThruWordTwoText="thru"
    app:cardholderCvv="124"
    app:cardMonthYearText="MONTH/YEAR"
    app:cardInformationText="Legal - and usually boring - text" />
```


You are also able to link CardView to your own pre-built form, just by passing your EditText references to it through nice methods like setNameTextInput(), setNumberTextInput() and so on. Everything else will already be configured for you :)

``` java
CardView card = (CardView) rootView.findViewById(R.id.cardview);
card.setNameTextInput(etName);
card.setNumberTextInput(etNumber);
card.setExpirationTextInput(etExpiration);
card.setCvvTextInput(etCvv);
```

- - -
### Featured Projects
Are you using this library in one of your projects? Send me a message so it can be featured here!


### License
=======

    Copyright 2014 Júlio Zynger

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.