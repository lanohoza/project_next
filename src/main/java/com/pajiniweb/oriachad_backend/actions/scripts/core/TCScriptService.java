package com.pajiniweb.oriachad_backend.actions.scripts.core;

import com.pajiniweb.oriachad_backend.actions.domains.entities.Script;
import com.pajiniweb.oriachad_backend.actions.repositories.ScriptRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TCScriptService {
	private Map<String, IScriptImplementation> scripts;
	@Autowired
	private ScriptRepository scriptRepository;

	public TCScriptService() {
		scripts = new HashMap<>();
	}

	public void register(String code, IScriptImplementation script) {
		if (!scriptRepository.findByCode(code).isPresent())
			scriptRepository.save(Script.builder().code(code).build());
		scripts.put(code, script);
	}

	public boolean runScript(String code) throws Exception {
		return scripts.get(code).run(null);
	}

	public List<IScriptImplementation> getScripts() {
		return scripts.values().stream().toList();
	}
}
