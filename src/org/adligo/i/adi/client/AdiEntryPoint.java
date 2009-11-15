package org.adligo.i.adi.client;

import org.adligo.i.adi.client.models.CacheRemoverToken;
import org.adligo.i.adi.client.models.CacheWriterToken;
import org.adligo.i.adi.client.models.ConfigRequest;
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
		holder.addUsed(new CacheReader());
		holder.addUsed(new CacheRemover());
		holder.addUsed(new CacheWriter());
		//impl requires gwt_util
		holder.addUsed(CheckedInvokerList.class);
		holder.addUsed(CheckedRegistry.class);
		holder.addUsed(new DoNothingCheckedInvoker());
		holder.addUsed(new DoNothingInvoker());
		//impl requires gwt_util
		holder.addUsed(EventDelegator.class);
		holder.addUsed(I_CheckedInvoker.class);
		holder.addUsed(I_Invoker.class);
		holder.addUsed(new InvocationException());
		//impl requires gwt_util
		holder.addUsed(InvokerList.class);
		holder.addUsed(InvokerNames.class);
		holder.addUsed(new ProxyCheckedInvoker(""));
		holder.addUsed(new ProxyInvoker(""));
		holder.addUsed(Registry.class);
		holder.addUsed(new SimpleClock());
		holder.addUsed(new SimpleSystemOut());
		holder.addUsed(StandardInvokers.class);
		holder.addUsed(new SynchronizedCheckedInvokerProxy(new DoNothingCheckedInvoker()));
		holder.addUsed(new SynchronizedInvokerProxy(new DoNothingInvoker()));
		
		//impl requires gwt_util
		holder.addUsed(CacheRemoverToken.class);
		holder.addUsed(new CacheWriterToken());
		holder.addUsed(new ConfigRequest(null));
	}

}