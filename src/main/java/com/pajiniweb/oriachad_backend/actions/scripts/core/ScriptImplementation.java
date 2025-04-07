package com.pajiniweb.oriachad_backend.actions.scripts.core;

public abstract class ScriptImplementation implements IScriptImplementation {

	protected TCScriptService tcScriptService;
	protected String code;

	public ScriptImplementation(TCScriptService tcScriptService) {
		this.tcScriptService = tcScriptService;
		this.code = this.getCodeFromClassName();
		this.register();
	}

	public String getCodeFromClassName() {
		String className = this.getClass().getSimpleName();
		return className.replace("Script", "");
	}

	public String getCode() {
		return this.code;
	}

	@Override
	public void register() {
		tcScriptService.register(this.code, this);
	}

	public abstract boolean run(Object[] args) throws Exception;
}
