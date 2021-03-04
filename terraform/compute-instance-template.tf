resource "google_compute_instance_template" "sumanth_instance_template" {
  name        = "sumanth-tddapp-template"
  description = "This app for tdd application"

  tags = ["allow-ssh-http-https"]

  instance_description = "TDD application instance"
  machine_type         = "e2-micro"
  can_ip_forward       = false

  scheduling {
    automatic_restart   = true
    on_host_maintenance = "MIGRATE"
  }

  disk {
    source_image      = "projects/ubuntu-os-cloud/global/images/ubuntu-1604-xenial-v20210224"
    auto_delete       = true
    boot              = true
  }

  network_interface {
    network = google_compute_network.sumanth_vpc_network.self_link
    subnetwork = google_compute_subnetwork.sumanth_network-with-private-secondary-ip-ranges.self_link
  }

  metadata_startup_script = "sudo add-apt-repository ppa:openjdk-r/ppa -y && sudo apt-get update -y && sudo apt install openjdk-11-jdk -y && wget https://github.com/abilaashsai/GCP-Workshop/releases/download/1/demo-0.0.1-SNAPSHOT.jar && java -jar demo-0.0.1-SNAPSHOT.jar"
}