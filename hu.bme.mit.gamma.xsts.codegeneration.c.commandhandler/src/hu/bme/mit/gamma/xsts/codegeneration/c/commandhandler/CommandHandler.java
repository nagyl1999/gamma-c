package hu.bme.mit.gamma.xsts.codegeneration.c.commandhandler;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import hu.bme.mit.gamma.xsts.model.*;
import hu.bme.mit.gamma.xsts.codegeneration.c.*;

public class CommandHandler extends AbstractHandler {
	
	private static final Logger LOGGER = Logger.getLogger("hu.bme.mit.gamma.xsts.codegeneration.c.commandhandler");
	
	public Resource loadResource(URI uri) {
	    return new ResourceSetImpl().getResource(uri, true);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		/* read parameter from ui event */
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		Object element = selection.getFirstElement();
		
		/* in case of type mismatch throw exception */
		if (!(element instanceof IFile)) {
			LOGGER.severe("Invalid parameter: " + element);
			throw new IllegalArgumentException("Parameter type must be *.gsts");
		}
		
		/* retrieve xsts model */
		IFile file = (IFile) element;
		Resource res = loadResource(URI.createURI(file.getLocationURI().toString()));
		XSTS xsts = (XSTS) res.getContents().get(0);
		
		System.out.println(URI.createURI(file.getLocationURI().toString()).toString());
		
		LOGGER.info("XSTS model " + xsts.getName() + " successfully read.");
		
		/* build c code */
		CodeBuilder builder = new CodeBuilder(xsts);
		builder.constructHeader();
		builder.constructCode();
		builder.save();
		
		LOGGER.info("C code from model " + xsts.getName() + " successfully created.");
		
		return null;
	}

}
