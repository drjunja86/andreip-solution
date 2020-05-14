# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure("2") do |config|
  # The most common configuration options are documented and commented below.
  # For a complete reference, please see the online documentation at
  # https://docs.vagrantup.com.

  # Every Vagrant development environment requires a box. You can search for
  # boxes at https://vagrantcloud.com/search.
  config.vm.box = "hashicorp/bionic64"

  # Install needed packages
  #config.vm.provision :shell, inline: "apt update && apt install -y zsh"

  # Setup oh-my-zsh as default shell
  #config.vm.provision :shell, privileged: false, path: "./scripts/ohmyzsh.sh"
  #config.vm.provision :shell, inline: "chsh -s /bin/zsh vagrant"

  # Disable automatic box update checking. If you disable this, then
  # boxes will only be checked for updates when the user runs
  # `vagrant box outdated`. This is not recommended.
  # config.vm.box_check_update = false

  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine. In the example below,
  # accessing "localhost:8080" will access port 80 on the guest machine.
  # NOTE: This will enable public access to the opened port
  config.vm.network "forwarded_port", guest: 8080, host: 9080
  config.vm.network "forwarded_port", guest: 443, host: 9443
  config.vm.network "forwarded_port", guest: 5432, host: 65432

  # Share an additional folder to the guest VM. The first argument is
  # the path on the host to the actual folder. The second argument is
  # the path on the guest to mount the folder. And the optional third
  # argument is a set of non-required options.
  config.vm.synced_folder "./", "/vagrant_data"

  # Provider-specific configuration so you can fine-tune various
  # backing providers for Vagrant. These expose provider-specific options.
  # Example for VirtualBox:
  #
  config.vm.provider "virtualbox" do |vb|
  #   # Display the VirtualBox GUI when booting the machine
  #   vb.gui = true
  #
  #   # Customize the amount of memory on the VM:
  #   # NOTE: the default is 1024
     vb.memory = "2048"
  end

  #config.vm.provision "ansible_local" do |ansible|
  #  ## Uncomment to turn on debug output
  #  # ansible.verbose = "vvv"
  #  ansible.playbook = "ansible/prepare_ansible.yml"
  #end
  
  config.vm.provision "ansible_local" do |ansible|

    ansible.tags = ["provisioning", "vagrant"]
    #ansible.skip_tags = ["provisioning"]

    ## Uncomment to turn on debug output
    #ansible.verbose = "vvv"
    ansible.playbook = "ansible/playbook.yml"
    ansible.inventory_path = "ansible/environments/dev"
    ansible.limit='all'
    #ansible.vault_password_file="vaultpass"
  end
end
