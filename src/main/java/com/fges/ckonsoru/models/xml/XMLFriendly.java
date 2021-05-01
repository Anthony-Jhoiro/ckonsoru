package com.fges.ckonsoru.models.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Interface that represents an xml friendly element that can be insert in a xml database
 */
public interface XMLFriendly {
    /**
     * Get the xml collection corresponding to the item type
     * @return a string representing the class xml collection
     */
    String getCollectionName();

    /**
     * Parse the object to create an xml element.
     * @param doc doc used to create the document
     * @return an Element that corresponds to the object
     */
    Element toXMLElement(Document doc);
}
