package com.zakisupermarket.tools;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

// Vendor-only tool - run this locally whenever a store pays for another month
// (or, for a one-time-purchase sale, once at first activation with a very
// long "months" value), NEVER deploy it (and never deploy the private key
// file it reads) to a customer's machine. It just prints a code to paste into
// the app's license/renewal screen. Plain main(), no Spring context needed:
//
//   mvn compile exec:java -Dexec.mainClass=com.zakisupermarket.tools.LicenseCodeGenerator \
//       -Dexec.args="E:/Backend/Projects/zaki-license-keys/license-private.pem <licenseKey> 1200"
//
// args: <private-key-pem-path> <licenseKey> <months>
//
// <licenseKey> is the UUID shown on the customer's own license/renewal screen
// (Store.licenseKey) - NOT the numeric store id. Every customer runs their
// own separate, offline database, so every customer's first store would
// otherwise get id=1, and a code bound to that id would unlock every other
// customer's first store too.
public final class LicenseCodeGenerator {

    private LicenseCodeGenerator() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: LicenseCodeGenerator <private-key-pem-path> <licenseKey> <months>");
            System.exit(1);
        }

        Path privateKeyPath = Path.of(args[0]);
        String licenseKey = args[1];
        int months = Integer.parseInt(args[2]);

        PrivateKey privateKey = loadPrivateKey(privateKeyPath);
        Date expiresAt = Date.from(addMonths(Instant.now(), months));

        String code = Jwts.builder()
                .claim("licenseKey", licenseKey)
                .setIssuedAt(new Date())
                .setExpiration(expiresAt)
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();

        System.out.println("License key: " + licenseKey);
        System.out.println("Expires at:  " + expiresAt.toInstant().atZone(ZoneId.systemDefault()));
        System.out.println();
        System.out.println("Activation/renewal code (send this to the customer):");
        System.out.println(code);
    }

    private static Instant addMonths(Instant now, int months) {
        return now.atZone(ZoneId.systemDefault()).plusMonths(months).toInstant();
    }

    private static PrivateKey loadPrivateKey(Path pemPath) throws IOException, GeneralSecurityException {
        String pem = Files.readString(pemPath, StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decoded = Base64.getDecoder().decode(pem);
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }
}
