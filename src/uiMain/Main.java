/* Autores: Samuel Botero Rivera, Santiago Sanchez Ruiz, Samuel Gutierrez Betancur, Samuel Garcia Rojas  */
package uiMain;

import baseDatos.Serializador;
import gestorAplicacion.administracion.CategoriaHabitacion;
import gestorAplicacion.administracion.HistoriaClinica;
import gestorAplicacion.administracion.Hospital;
import gestorAplicacion.administracion.Medicamento;
import gestorAplicacion.administracion.Vacuna;
import gestorAplicacion.personas.Doctor;
import gestorAplicacion.personas.Enfermedad;
import gestorAplicacion.personas.Paciente;
import gestorAplicacion.servicios.Cita;
import gestorAplicacion.servicios.CitaVacuna;
import gestorAplicacion.servicios.Formula;
import gestorAplicacion.servicios.Habitacion;
import gestorAplicacion.servicios.Servicio;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Hospital hospital = new Hospital(); // Inicio de instancia de hospital para serialización
        mostrarUtilidadPrograma();
        menuInicial(hospital);
    }

    public static void menuInicial(Hospital hospital) {

        byte opcion; // Se inicia la variable para elegir la opción que se selecciona

        do {
            // Inicio del programa, solo se selecciona a que menú se desea acceder
            System.out.println("\nMENU INICIAL");
            System.out.println("1. Servicios para pacientes");
            System.out.println("2. Gestionar registros");
            System.out.println("3. --Salir--");
            System.out.println("Ingrese una opcion: ");
            opcion = sc.nextByte();

            switch (opcion) {
                case 1 -> MenuFuncionalidades.menuFuncionalidades(hospital);
                case 2 -> MenuGestion.menuGestion(hospital);
                case 3 -> {
                    Serializador.serializar(hospital);
                    System.exit(0);
                }
            }
        } while (true);
    }
    public static void mostrarUtilidadPrograma() {
        System.out.println("Utilidadez del programa:");
        System.out.println("- Gestión de pacientes, doctores y medicamentos.");
        System.out.println("- Asignación de habitaciones en el hospital.");
        System.out.println("- Registro y manejo de citas médicas y de vacunación.");
        System.out.println("- Generación de facturas y control de pagos.");
        System.out.println("- Manejo de inventario de medicamentos y vacunas.");
    }
public static void menuFuncionalidades(Hospital hospital) {
        byte opcion;

        do {
            System.out.println("\nMENU FUNCIONALIDADES");
            System.out.println("1. Agendar una cita medica");
            System.out.println("2. Generar fórmula médica");
            System.out.println("3. Asignar habitación a un paciente");
            System.out.println("4. Aplicarse una vacuna");
            System.out.println("5. Facturacion");
            System.out.println("6. --Regresar al menu inicial--");
            System.out.println("7. --Salir--");
            System.out.println("Ingrese una opcion: ");
            opcion = sc.nextByte();

            switch (opcion) {
                case 1 -> agendarCitas(hospital);
                case 2 -> formulaMedica(hospital);
                case 3 -> asignarHabitacion(hospital);
                case 4 -> vacunacion(hospital);
                case 5 -> facturacion(hospital);
                case 6 -> {
                    return;
                }
                case 7 -> {
                    Serializador.serializar(hospital);
                    System.exit(0);
                }
            }
        } while (true);
    }

public static void menuGestion(Hospital hospital) {
        byte opcion;
        // Menú para gestionar las clases del programa
        do {
            System.out.println("\nMENU Gestion");
            System.out.println("1. Gestionar Pacientes");
            System.out.println("2. Gestionar apartado de vacunas");
            System.out.println("3. Gestionar Doctores");
            System.out.println("4. Gestionar Hospital");
            System.out.println("5. --Regresar al menu inicial--");
            System.out.println("6. --Salir--");
            System.out.println("Ingrese una opcion: ");
            opcion = sc.nextByte();
            switch (opcion) {
                case 1 -> menuGestionPaciente(hospital);
                case 2 -> menuGestionVacunas(hospital);
                case 3 -> menuGestionDoctor(hospital);
                case 4 -> menuGetionHospital(hospital);
                case 5 -> {
                    return;
                }
                case 6 -> {
                    Serializador.serializar(hospital);
                    System.exit(0);
                }
            }
        } while (true);
    }




	///////////////////////////////// FUNCIONALIDADES ////////////////////////////////////////
	
	public static void agendarCitas(Hospital hospital) {

		//pide la cedula y busca el paciente
        System.out.print("Ingrese su numero de cedula: ");

		int numeroCedula;
		numeroCedula = sc.nextInt();

		Paciente pacienteAsignado = hospital.buscarPaciente(numeroCedula);

		//Verificación que el paciente se encuentre en la base de datos del hospital

        if (pacienteAsignado == null){
            while(true){
                //Se da la posibilidad de registrar ese paciente
                System.out.println("El paciente no está registrado.\n¿Desea registrarlo?");
                System.out.println("1. Si\n2.No \nSeleccione una opción");
                byte opcion= sc.nextByte();
                switch (opcion){
                    case 1:
                        registrarPaciente(hospital);
                        return;

                    case 2:
                        System.out.println("Adiós");
                        return;

                    default:
                        System.out.println("Opción Inválida");
                }
            }
        }

		//Mensaje de bienvenida
		System.out.println(pacienteAsignado.bienvenida());

		//se crea un arraylit de doctores que se usara mas adelante
		ArrayList<Doctor> doctoresDisponibles = new ArrayList<Doctor>();

		//ciclo while para cuando no hay doctores del tipo de eps del paciente en la especialidad seleccionada
		while (doctoresDisponibles.size()==0) {

		//menu para elegir el tipo de cita
		byte tipoCita;

		System.out.println("\nSeleccione el tipo de cita que requiere");
		System.out.println("1. General");
		System.out.println("2. Odontologia");
		System.out.println("3. Oftalmologia");
		System.out.println("4. --Regresar al menu--");
		System.out.print("Ingrese una opcion: ");

		tipoCita = sc.nextByte();

		//ciclo while para casos de error
		while ((tipoCita<1) ||(tipoCita>4) ){
            System.out.print("Opción fuera de rango, por favor ingrese otro número: ");
            tipoCita = sc.nextByte();
		}

		//muestra los doctores disponibles segun el tipo de cita y eps del paciente
		switch (tipoCita) {
		case 1:
			doctoresDisponibles = pacienteAsignado.buscarDoctorEps("General", hospital);

			if (doctoresDisponibles.size()>0) {
				System.out.println("\nLista de doctores Generales para el tipo de eps "
						+pacienteAsignado.getTipoEps()+":");
			for(int i=1; i<=doctoresDisponibles.size(); i++) {
	    		System.out.println(i + ". " + doctoresDisponibles.get(i-1).getNombre());
			}
		}
			else {
				System.out.println("\nNo hay doctores que atiendan al tipo de eps "
						+pacienteAsignado.getTipoEps()+" para esta categoria, por favor seleccione otra");
			}
			break;

		case 2:
			doctoresDisponibles = pacienteAsignado.buscarDoctorEps("Odontologia", hospital);
			if (doctoresDisponibles.size()>0) {
				System.out.println("\nLista de odontologos para el tipo de eps "
						+pacienteAsignado.getTipoEps()+":");
				for(int i=1; i<=doctoresDisponibles.size(); i++) {
		    		System.out.println(i + ". " + doctoresDisponibles.get(i-1).getNombre());
				}
			}
				else {
					System.out.println("\nNo hay doctores que atiendan al tipo de eps "
						+pacienteAsignado.getTipoEps()+" para esta categoria, por favor seleccione otra");
				}
			break;

		case 3:
			doctoresDisponibles = pacienteAsignado.buscarDoctorEps("Oftalmologia", hospital);
			if (doctoresDisponibles.size()>0) {
				System.out.println("\nLista de oftalmologos para el tipo de eps "
						+pacienteAsignado.getTipoEps()+":");
				for(int i=1; i<=doctoresDisponibles.size(); i++) {
		    		System.out.println(i + ". " + doctoresDisponibles.get(i-1).getNombre());
				}
			}
				else {
					System.out.println("\nNo hay doctores que atiendan al tipo de eps "
						+pacienteAsignado.getTipoEps()+" para esta categoria, por favor seleccione otra");
				}
			break;

			case 4:
				return;
		}
	}
		//se crea un arraylit de citas del doctor seleccionado
		ArrayList<Cita> agendaDisponible = new ArrayList<Cita>();

		//ciclo while para cuando el doctor seleccionado no tiene citas disponibles
		while(agendaDisponible.size()==0){
			//elige un doctor de la lista anterior
			System.out.print("\nSeleccione el doctor con el que quiere la cita: ");

			byte numeroDoctor;
			numeroDoctor = sc.nextByte();

			//ciclo while para casos de error
			while ((numeroDoctor<1) || numeroDoctor>doctoresDisponibles.size()+1){
				System.out.print("Opción fuera de rango, por favor ingrese otro número: ");
				numeroDoctor = sc.nextByte();
			}

			if(numeroDoctor==doctoresDisponibles.size()+1){return;}

			Doctor doctorAsignado = doctoresDisponibles.get(numeroDoctor-1);
			agendaDisponible = doctorAsignado.mostrarAgendaDisponible();

			if(agendaDisponible.size()>0) {
				//muestra la agenda disponible del doctor seleccionado
				System.out.println("\nCitas disponibles: ");
				for (int i = 1; i <= agendaDisponible.size(); i++) {
					System.out.println(i + ". " + agendaDisponible.get(i - 1).getFecha());
				}

				//selecciona la cita de la agenda disponible del doctor
				System.out.print("\nSeleccione la cita de su preferencia: ");

				byte numeroCita;
				numeroCita = sc.nextByte();

				//ciclo while para casos de error
				while ((numeroCita<1) || numeroCita>agendaDisponible.size()){
					System.out.print("Opción fuera de rango, por favor ingrese otro número: ");
					numeroCita = sc.nextByte();
				}

				//actualiza la agenda del doctor con el paciente
				Cita citaAsignada = doctorAsignado.actualizarAgenda(pacienteAsignado, numeroCita, agendaDisponible);

				System.out.println("\nSu cita ha sido asignada con exito");

				//actualiza el historial de citas del paciente
				pacienteAsignado.actualizarHistorialCitas(citaAsignada);


				//resumen de la cita
				System.out.println("\nResumen de su cita: ");
				System.out.println("Fecha: " + citaAsignada.getFecha());
				System.out.println("Paciente: " + citaAsignada.getPaciente().getNombre());
				System.out.println("Doctor: " + citaAsignada.getDoctor().getNombre());


				//limpia los arrays de doctores y agenda disponibles al haberse asignado un paciente a una cita
				agendaDisponible.clear();
				doctoresDisponibles.clear();

				//muestra el historial de las citas que ha tenido el paciente
				System.out.println("\nhistorial citas de historia clinica del paciente: ");
				for(int i=1; i<=pacienteAsignado.getHistoriaClinica().getHistorialCitas().size(); i++) {
					System.out.println("");
					System.out.println("Fecha: " + pacienteAsignado.getHistoriaClinica().getHistorialCitas().get(i-1).getFecha());
					System.out.println("Paciente: " + pacienteAsignado.getHistoriaClinica().getHistorialCitas().get(i-1).getPaciente().getNombre());
					System.out.println("Doctor: " + pacienteAsignado.getHistoriaClinica().getHistorialCitas().get(i-1).getDoctor().getNombre());
				}

				//Despedida
				System.out.println("\n"+pacienteAsignado.despedida(citaAsignada));
				break;
			}
			else{
				System.out.println("\nEste doctor no tiene citas disponibles, por favor seleccione otro");
			}
		}
	}

	
	public static void formulaMedica(Hospital hospital) {
        //Se pide la cedula del paciente y se arroja el paciente
        System.out.println("Ingrese la cédula del paciente: ");
        int cedula = sc.nextInt();
        Paciente paciente = hospital.buscarPaciente(cedula);
        if (paciente == null) { /*Si el paciente es null, quiere decir que no lo encontró, por lo que
        preguntamos si desea registrar este paciente */
            while (true) {
                System.out.println("El paciente no esta registrado.\n¿Desea registrarlo?");
                System.out.println("1. Si\n2. No \nSeleccione una opción");
                byte opcion = sc.nextByte();
                switch (opcion) {
                    case 1:
                        registrarPaciente(hospital);
                        return;

                    case 2:
                        System.out.println("Adios");
                        return;
                    default:
                        System.out.println("Opción Inválida");
                }
            }
        }
        // se crea ya la formula con el paciente asociado
        Formula formulaPaciente = new Formula(paciente);
        // se inicia su lista de medicamentos solicitada vacia
        ArrayList<Medicamento> listMedicamento = new ArrayList<Medicamento>();
        // se pregunta al paciente que enfermedad desea tratar y se busca la lista de enfermedades en su historia
        System.out.println("¿Que enfermedad deseas tratar?");
        Enfermedad enfermedadTratar = null;
        if (paciente.getHistoriaClinica().getEnfermedades().size() == 0){ //Caso en el que el paciente no tenga enfermedades registradas
            System.out.println("No hay enfermedades registradas, por favor diríjase a la sección de registrar enfermedades.");
            return;
        }
        while (true) { //Bucle para el caso en el que elija opciones fuera de rango
            for (int i = 0; i < paciente.getHistoriaClinica().getEnfermedades().size(); i++) {
                System.out.println(i + 1 + "." + paciente.getHistoriaClinica().getEnfermedades().get(i));
            }
            byte opcEnf = sc.nextByte();
            if (opcEnf <= paciente.getHistoriaClinica().getEnfermedades().size() && opcEnf > 0) {
                enfermedadTratar = paciente.getHistoriaClinica().getEnfermedades().get(opcEnf - 1); //Aquí seleccionamos que enfermedad eligió el paciente a tratar
                break;

            } else {
                System.out.println("Opción Inválida");
            }
        }
        ArrayList<Doctor> doctoresCita = paciente.getHistoriaClinica().buscarCitaDoc(enfermedadTratar.getEspecialidad(), hospital); /* metodo de la historia del paciente
         para buscar que doctor trata la enfermedad y que haya tenido una cita con el paciente */
        if (doctoresCita.size() == 0) { //Caso donde no tenga citas con ningún doctor que trate su enfermedad
            System.out.println("Ahora no contamos con doctores para tratar esta enfermedad. Lo sentimos mucho");
            return;
        }
        Doctor doctorEscogido = null;
        while (true) { //Bucle para el caso en el que elija opciones fuera de rango
            System.out.println("Los doctores que lo han atendido y están disponibles para formular " + enfermedadTratar.getNombre() + " " + enfermedadTratar.getTipologia() + " son: ");
            //Imprime la cita de los doctores que tratan su enfermedad y tuvieron una cita con él
            for (int i = 0; i < doctoresCita.size(); i++) {
                System.out.println(i + 1 + "." + doctoresCita.get(i).getNombre());
            }
            byte opcDoc = sc.nextByte();
            if (opcDoc > 0 & opcDoc <= doctoresCita.size()) {
                doctorEscogido = doctoresCita.get(opcDoc - 1); //seleccionamos el doctor
                break;
            } else {
                System.out.println("Opción inválida");
            }
        }
        boolean agregarOtro = false; //condicion para el while para ir agregando medicamentos

        formulaPaciente.setDoctor(doctorEscogido);
        //bucle para agregar medicamentos, cada vez q pregunte si desea otro y responda si se repite
        do {
            System.out.println(paciente.mensajeDoctor(doctorEscogido));
            //Aquí buscamos los medicamentos disponibles y los medicamentos que traten la enfermedad del paciente
            ArrayList<Medicamento> disponibleEnf = paciente.medEnfermedad(enfermedadTratar, hospital);
            //Condicion para que cuando ya no haya medicamentos disponibles no siga el bucle y lo termine
            if (disponibleEnf.size()==0){
                System.out.println("No hay más medicamentos disponibles");
                break;
            }
            while (true) { //Bucle para el caso en el que elija opciones fuera de rango
                //esto solo se usa para imprimir la lista de la forma 1. Nombre, etc..
                for (int i = 0; i < disponibleEnf.size(); i++) {
                    System.out.println(i + 1 + "." + disponibleEnf.get(i) + ", Cantidad: " + disponibleEnf.get(i).getCantidad() + ", Precio: " + disponibleEnf.get(i).getPrecio());
                }
                //guarda la opcion elegida
                byte opcMed = sc.nextByte();
                if (opcMed <= 0 || opcMed > disponibleEnf.size()) {
                    System.out.println("Opción inválida");
                }
                /* si la opcion es valida se agrega a la lista de medicamentos que esta fuera del bucle y se ejecuta eliminarCantidad()
                para actualizar la cantidad de medicamentos en inventario */
                else {
                    Medicamento medicamentoEscogido = disponibleEnf.get(opcMed - 1);
                    medicamentoEscogido.eliminarCantidad();
                    listMedicamento.add(medicamentoEscogido);
                    break;
                }
            }
            /* Estas dos lineas son para ir actualizando la lista de medicamentos de la formula e ir actualizando el precio,
             se usa el metodo calcularPrecio de paciente, el cual se mete como parametro la lista de medicamentos que vamos llenando */
            formulaPaciente.setListaMedicamentos(listMedicamento);
            System.out.println("Esta es tu lista actual de medicamentos:" + listMedicamento);
            // Esto solo imprime los medicamentos que van
            for (int i = 0; i < listMedicamento.size(); i++) {
                System.out.println(i + 1 + "." + listMedicamento.get(i));
            }
            while (true) { //Bucle para el caso en el que se ingresen opciones incorrectas
                System.out.println("¿Desea agregar otro medicamento? (s/n)");
                char agregar = sc.next().charAt(0);
                if (agregar == 's' || agregar == 'n') {
                    agregarOtro = agregar == 's';
                    break;
                } else {
                    System.out.println("Opción inválida");
                }
            }
        } while (agregarOtro);
        if (listMedicamento.size() == 0) { /* En caso de que listaMedicamentos este vacía significa que no formulo medicamentos
         entonces que esa formula vacía creada al inicio de la funcionalidad no se guarde en la historia del paciente */
            return;
        }
        paciente.getHistoriaClinica().agregarFormula(formulaPaciente);
        //esto solo imprime el toString de la formula
        System.out.println(formulaPaciente);
        listMedicamento.clear();
    }
}
