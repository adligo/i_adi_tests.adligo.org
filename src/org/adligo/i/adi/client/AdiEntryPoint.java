package org.adligo.i.adi.client;

import org.adligo.i.adi.client.light.Cache;
import org.adligo.i.adi.client.light.CacheReader;
import org.adligo.i.adi.client.light.CacheRemover;
import org.adligo.i.adi.client.light.CacheWriter;
import org.adligo.i.adi.client.light.MemoryReader;
import org.adligo.i.adi.client.light.MemoryWriter;
import org.adligo.i.adi.client.light.Memory;
import org.adligo.i.adi.client.models.CacheRemoverToken;
import org.adligo.i.adi.client.models.CacheWriterToken;
import org.adligo.i.adi.client.models.ConfigRequest;
import org.adligo.i.adi.client.models.MemoryWriterToken;
import org.adligo.i.util.client.ClassUsageView;
import org.adligo.i.util.client.I_UsageHolder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class AdiEntryPoint implements EntryPoint {
	I_UsageHolder holder;
	ClassUsageView view;
	
	
	public AdiEntryPoint() {
		view = new ClassUsageView();
		holder = view;
	}
	public AdiEntryPoint(I_UsageHolder p_holder) {
		holder = p_holder;
	}
	
	@Override
	public void onModuleLoad() {
		if (view != null) {
			// TODO Auto-generated method stub
			RootPanel.get().add(view);
		}
		
		holder.addUsed(new BaseConfigProvider());
		holder.addUsed(Cache.class);
		holder.addUsed(CacheReader.INSTANCE);
		holder.addUsed(CacheRemover.INSTANCE);
		holder.addUsed(CacheWriter.INSTANCE);			
		//impl requires gwt_util
		holder.addUsed(CheckedInvokerList.class);
		holder.addUsed(CheckedRegistry.class);
		holder.addUsed(new DoNothingCheckedInvoker());
		holder.addUsed(new DoNothingInvoker());
		//impl requires gwt_util
		holder.addUsed(EventDelegator.class);
		holder.addUsed(I18nConstantsFactory.class);
		holder.addUsed(I_CheckedInvoker.class);
		holder.addUsed(I_Invoker.class);
		holder.addUsed(new InvocationException());
		//impl requires gwt_util
		holder.addUsed(InvokerList.class);
		holder.addUsed(InvokerNames.class);
		
		holder.addUsed(LightStandardInvokers.class);
		
		holder.addUsed(Memory.class);
		holder.addUsed(MemoryReader.INSTANCE);
		holder.addUsed(MemoryWriter.INSTANCE);
		
		holder.addUsed(new ProxyCheckedInvoker(""));
		holder.addUsed(new ProxyInvoker(""));
		holder.addUsed(Registry.class);
		holder.addUsed(SimpleClock.INSTANCE);
		holder.addUsed(SimpleSystemOut.INSTANCE);
		holder.addUsed(SimpleSystemErr.INSTANCE);
		holder.addUsed(StandardInvokers.class);
		holder.addUsed(new SynchronizedCheckedInvokerProxy(new DoNothingCheckedInvoker()));
		holder.addUsed(new SynchronizedInvokerProxy(new DoNothingInvoker()));
		
		//impl requires gwt_util
		holder.addUsed(CacheRemoverToken.class);
		holder.addUsed(new CacheWriterToken());
		holder.addUsed(new ConfigRequest(null));
		holder.addUsed(MemoryWriterToken.class);
	}

}
