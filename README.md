# PduConverters
PDU (Portable Data Unit) Converters implemented currently in Java and PHP.

I needed to convert between PDU and simple text for a USSD project. All I could find online were SMS PDU converters and they didn't work for me.
I found https://github.com/gnomeby/ussd and extracted the conversion code. The PHP version is a exactly what I took from the code.
The Java version is a direct port.

## Usage

For PHP
```php
require 'Converter.php';

use Pdu\Converter;

var_dump(Converter::toPDU('*124#')); //-> string 'AA988C3602' (length=10)
var_dump(Converter::toText('54747A0E6A97E7F3F0B90CBA87E7A0F1DB6D2FCBE9657208')); //-> string 'This message was converted!' (length=27)

```


For Java
```java
//copy the code into your project or however you do these things

Converter.toPDU("*124#");
Converter.toText("54747A0E6A97E7F3F0B90CBA87E7A0F1DB6D2FCBE9657208");

```

## Copyright
Please use as you please. 
If you can, contribute improvements you make to the code. 
If you port it into another language, it would be great if you contributed that back as well. 
We have a suprising vacuum in this area online.
