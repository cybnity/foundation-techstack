package org.cybnity.application.asset_control.ui.system.backend.routing;

import io.vertx.core.AsyncResult;

public class ResourceAccessAuthorizationResult<T> implements AsyncResult<Boolean> {

	private Boolean status;
	private Throwable cause;

	public ResourceAccessAuthorizationResult(Boolean succeeded, Throwable cause) {
		this.status = succeeded;
		this.cause = cause;
	}

	@Override
	public Boolean result() {
		return status;
	}

	@Override
	public Throwable cause() {
		return this.cause;
	}

	@Override
	public boolean succeeded() {
		return this.status.booleanValue();
	}

	@Override
	public boolean failed() {
		return (!this.status);
	}

}
