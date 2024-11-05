package javassistrecordsealedtests;

import java.io.File;
import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.PermittedSubclassesAttribute;
import javassist.bytecode.RecordAttribute;
import javassist.bytecode.RecordAttribute.RecordComponentInfo;
import javassistrecordsealedtests.test.RecordTest;
import javassistrecordsealedtests.test.Sealed;

public class Tests {

	public static void main(String[] args) {
		ClassPool.getDefault().appendClassPath(new ClassClassPath(Sealed.class));
		ClassPool.getDefault().appendClassPath(new ClassClassPath(RecordTest.class));

		try {
			CtClass clazz = ClassPool.getDefault().get("javassistrecordsealedtests.test.Sealed");
			CtClass rec = ClassPool.getDefault().get("javassistrecordsealedtests.test.RecordTest");

			PermittedSubclassesAttribute permitted = (PermittedSubclassesAttribute)clazz.getClassFile().getAttribute(PermittedSubclassesAttribute.tag);
			for(String str:permitted.getPermittedSubclasses()) {
				System.out.println(str);
			}
			System.out.println("Adding Class");
			permitted.addClass("clazz.to.ToAdd");
			for(String str:permitted.getPermittedSubclasses()) {
				System.out.println(str);
			}
			
			
			
			clazz.writeFile();
			
			File folder = new File("pruned");
			folder.mkdirs();
			clazz.defrost();
			clazz.getClassFile().compact();
			clazz.writeFile("pruned");
			File renamed = new File("pruned_renamed");
			renamed.mkdirs();
			clazz.defrost();
			clazz.replaceClassName("clazz/to/ToAdd", "clazz/to/ToAddRenamed");
			clazz.writeFile("pruned_renamed");
			
			
			System.out.println("Record");
			RecordAttribute attr = (RecordAttribute)rec.getClassFile().getAttribute(RecordAttribute.tag);
			for(RecordComponentInfo info:attr.getComponents()) {
				System.out.println(info.getDescriptor());
				System.out.println(info.getName());
			}
			rec.writeFile();
			rec.defrost();
			rec.getClassFile().compact();
			rec.writeFile("pruned");
			rec.defrost();
			rec.replaceClassName("java/lang/Object", "clazz/to/ToAddRenamed");
			rec.writeFile("pruned_renamed");
		} catch (NotFoundException | IOException | CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
