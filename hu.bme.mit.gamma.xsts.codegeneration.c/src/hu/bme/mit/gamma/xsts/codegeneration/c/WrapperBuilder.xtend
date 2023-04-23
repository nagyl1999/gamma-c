package hu.bme.mit.gamma.xsts.codegeneration.c

import hu.bme.mit.gamma.xsts.model.XSTS
import org.eclipse.emf.common.util.URI
import hu.bme.mit.gamma.xsts.codegeneration.c.model.CodeModel
import hu.bme.mit.gamma.xsts.codegeneration.c.model.HeaderModel
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class WrapperBuilder implements IStatechartCode {
	
	private XSTS xsts;
	private String name;
	private String stName;
	
	private CodeModel code;
	private HeaderModel header;
	
	public new(XSTS xsts) {
		this.xsts = xsts
		this.name = xsts.name.toFirstUpper + "Wrapper";
		this.stName = name + "Statechart";
		/* code files */
		this.code = new CodeModel(name);
		this.header = new HeaderModel(name);
	}
	
	override constructHeader() {
		
	}
	
	override constructCode() {
		
	}
	
	override save(URI uri) {
		/* create src-gen if not present */
		var URI local = uri.appendSegment("src-gen");
		if (!new File(local.toFileString()).exists())
			Files.createDirectories(Paths.get(local.toFileString()));
			
		/* create c codegen folder if not present */
		local = local.appendSegment(xsts.name.toLowerCase)
		if (!new File(local.toFileString()).exists())
			Files.createDirectories(Paths.get(local.toFileString()));
		
		/* save models */
		code.save(local);
		header.save(local);
	}
	
	
	
}