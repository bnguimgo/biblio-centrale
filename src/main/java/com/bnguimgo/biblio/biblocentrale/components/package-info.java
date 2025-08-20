/**
 * Ce package gère les exceptions tout en s'assurant qu'aucun attribut n'est null
 * il permet d'éviter l'erreur suivante : Not annotated parameter overrides @NonNullApi parameter
 * <p>
 *     Le fichier package-info.java joue deux rôles :<br />
 *     1-) Il permet de rédiger la documentation du projet ou de décrire ce que fait le package<br />
 *     2-) Il permet également de déclarer  une ou plusieurs annotations applicables à toutes les classes du package
 * </p>
 * <p>
 *     Supposons qu'on ait besoin de déclarer une annotation pour tout le package, alors le fichier <b>package-info.java</b>
 *     est le fichier le plus indiqué pour le faire
 * </p>
 *     @NonNullApi Cette annotation indique qu'aucun paramètre, ni une valeur de retour ne peut être nul dans ce package.
 *     @NonNullFields Aucun attribut ou propriété d'une classe de ce package ne peut être nul.
 */
@NonNullApi
@NonNullFields
package com.bnguimgo.biblio.biblocentrale.components;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;