# -*- encoding: utf-8 -*-
require File.expand_path('../lib/vfnetapis/version', __FILE__)

Gem::Specification.new do |gem|
  gem.authors       = ["OneAPI"]
  gem.email         = ["kevin.smith@vodafone.com"]
  gem.description   = "OAUTH with OneAPI calls against Vodafone's OneAPI endpoint."
  gem.summary       = "Network API helpers for reading a user's location and brokering the OAUTH dance."
  gem.homepage      = ""

  gem.executables   = `git ls-files -- bin/*`.split("\n").map{ |f| File.basename(f) }
  gem.files         = `git ls-files`.split("\n")
  gem.test_files    = `git ls-files -- {test,spec,features}/*`.split("\n")
  gem.name          = "vfnetapis"
  gem.require_paths = ["lib"]
  gem.version       = Vfnetapis::VERSION
end



