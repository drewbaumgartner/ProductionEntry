package production.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of ProductionEntry objects.  This is used for saving the list to XML.
 * @author Drew
 *
 */
@XmlRootElement(name = "productionEntries")
public class ProductionEntryListWrapper {
	private List<ProductionEntry> productionEntries;
	
	@XmlElement(name = "productionEntry")
	public List<ProductionEntry> getProductionEntries()
	{
		return productionEntries;
	}
	
	public void setProductionEntries(List<ProductionEntry> productionEntries)
	{
		this.productionEntries = productionEntries;
	}
}
