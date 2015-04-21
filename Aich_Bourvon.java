public class Aich_Bourvon {
	
	public static final int NE_MAX = 100;
          
	
	static class Ascenceur {
		int dir;
		int nbP;
		int nE;
		int nPMax = 10;
		boolean plein;
		int pos; };
	
	static class Etage {
		int nbP;
		double pbE =0.002 ; };
	
	static class Tour {
		Ascenceur ascenceur;
		int nE = 20; };
	
	
     
              
        /**
        * Fonction du calcul de la presence d'une personne a chaque etage en un temps donne et en prenant en compte probabilite choisi par l'utilisateur.
        * @param ti represente la tour 
        * @param e represente le tableau d'etage 
        * @param ei represente un etage
        * @return void aucun retour
        **/
        
        
	static void presence (Tour ti, Etage e[], Etage ei) {
		
		                
		int i ; 
		double x = 0;
		
		for (i=0 ; i<ti.nE ; i++) {
			x = Math.random();
			
			if (x < ei.pbE)
				e[i].nbP++; 
		}
				
	}
	
	/**
	* Fonction qui regarde si un appel quelconque a ete effectue dans la tour.
	* @param ti represente la tour, 
	* @param e represente le tableau d'etage
	* @param tEA represente le tableau de boolean des etages ou il y'a au moins une personne
	* @param tNbPE represente un tableau d'entier des etages appeles par les utilisateurs dans l'ascenceur
	* @return ap un boolean si il y'a un appel ou non
	**/

	static boolean appel (Tour ti, Etage e[], boolean tEA[], int tNbPE[]) {
	
		int i;
		boolean ap=false;
		
		for (i=0 ; i<ti.nE ; i++) {
			if (e[i].nbP > 0 || tNbPE[i] > 0 ) {
				tEA[i]=true;
				ap=true;
			}
			else 
				tEA[i]=false;
		}

	
		return ap;
	}
	
	/**
	* Fonction qui teste si il y'a un appel plus haut par rapport à la position de l'ascenceur
	* @param ai represente l'ascenceur 
	* @param tEA represente le tableau de boolean des etages ou il y'a au moins une personne
	* @return testMax un boolean si il y'a un appel plus haut
	**/
	
	static boolean testAppelPlusHaut (Ascenceur ai, boolean tEA[]) {
		
		int i;
		boolean testMax = false;

		for (i=0 ; i<ai.nE ; i++) {
			if (tEA[i] == true && ((ai.nE-i-1)*30<ai.pos)) {
				testMax = true;
			}
		}
		return testMax;
	}
	
	/**
	* Fonction qui teste si il y'a un appel plus bas par rapport à la position de l'ascenceur
	* @param ai represente l'ascenceur 
	* @param tEA represente le tableau de boolean des etages ou il y'a au moins une personne
	* @return testMin un boolean si il y'a un appel plus bas
	**/
	
	static boolean testAppelPlusBas (Ascenceur ai, boolean tEA[]) {
		
		int i;
		boolean testMin = false;

		for (i=0 ; i<ai.nE ; i++) {
			if (tEA[i] == true && ((ai.nE-i-1)*30>ai.pos)) {
				testMin = true;
			}
		}
		return testMin;
	}
	
	
	/**
	* Fonction qui teste si il y'a un appel au meme etage de la position de l'ascenceur
	* @param ai represente l'ascenceur 
	* @param tEA represente le tableau de boolean des etages ou il y'a au moins une personne
	* @return test un boolean si il y'a un appel plus bas
	**/
	
	static boolean testAppelEgale (Ascenceur ai, boolean tEA[]) {
		
		int i;
		boolean testEgale = false;

		for (i=0 ; i<ai.nE ; i++) {
			if (tEA[i] == true && ((ai.nE-i-1)*30==ai.pos)) {
				testEgale = true;
			}
		}
		return testEgale;
	}
	
	
	/**
	* Fonction qui deplace l'ascenceur suivant la direction choisie
	* @param ai represente l'ascenceur
	* @return void aucun retour
	**/	
	
	static void deplacement (Ascenceur ai) {

		if (ai.dir == 1)
			ai.pos-=10;
		else if (ai.dir == -1)
			ai.pos+=10;
	}
	
	/**
	* Fonction qui fait descendre une personne de l'ascenceur
	* @param ai represente l'ascenceur
	* @param e represente le tableau d'etage
	* @param tEA represente le tableau de boolean des etages ou il y'a au moins une personne
	* @param tNbPE represente un tableau d'entier des etages appeles par les utilisateurs dans l'ascenceur
	* @return void aucun retour
	**/
	
	static void descend (Ascenceur ai, Etage e[], boolean tEA[], int tNbPE[]) {
	
		
		int x = ai.nE - (ai.pos/30)-1;
		
		if (tEA[x] == true && ai.pos%30 == 0) {
			while (tNbPE[x] > 0) {
				tNbPE[x]--;
				ai.nbP--;
			}	
		}
	
	}

	/**
	* Fonction qui fait monte une personne de l'ascenceur
	* @param ai represente l'ascenceur
	* @param e represente le tableau d'etage
	* @param tEA represente le tableau de boolean des etages ou il y'a au moins une personne
	* @param tNbPE represente un tableau d'entier des etages appeles par les utilisateurs dans l'ascenceur
	* @return void aucun retour
	**/
		
	static void monte (Ascenceur ai, Etage e[], boolean tEA[], int tNbPE[]) {
		
		
		int x = ai.nE - (ai.pos/30)-1;
	
		if (tEA[x] == true && ai.pos%30 == 0) {
			while (e[x].nbP > 0 && ai.nbP < ai.nPMax) {
				destination (tNbPE, ai, tEA, x);
				e[x].nbP--;
				ai.nbP++;
			}
		}	
						
		
	}
	
	/**
	* Fonction qui genere un etage aleatoire parmis ceux possible pour chaque passager entre dans l'ascenceur
	* @param t represente un tableau d'entier des etages appeles par les utilisateurs dans l'ascenceur (tNbPE dans d'autres fonctions)
	* @param ai represente l'ascenceur
	* @param tEA represente le tableau de boolean des etages ou il y'a au moins une personne
	* @param posP represente la position du passager pour ne pas que ca destination soit son départ
	* @return void aucun retour
	**/

	static void destination ( int t[], Ascenceur ai, boolean tEA [], int posP) {
	
		int i;
		int x = 0;

		
			do {
				x = (int)(Math.random()*ai.nE);
			} while (posP == x);
			
			t[x]++;
			tEA[x]= true;
			
	}
			
	/**
	* Fonction d'affichage, cette fonction est la principale de mon programme 
	* @param ti represente la tour 
	* @param ei represente un etage 
	* @param ai represente l'ascenceur
	* @return void aucun retour
	**/
			
			

	static void affichage (Tour ti, Etage ei, Ascenceur ai) {
                
		ai.pos = 30*(ti.nE-1);	
		ai.dir = 1;
	
		int temps = 0;
		int y;
		int max = 0;
		int min = 0;
		boolean testMax;
		boolean testMin;
		boolean testEgale;
                boolean end = false;
		boolean ap;
		
		int i,j,x;


		Etage tE [] = new Etage [ti.nE];
		boolean tEA [] = new boolean [ti.nE] ;
		int tNbPE [] = new int [ti.nE] ; 

		for (i = 0 ; i<ti.nE ; i++) 
			tE[i] = new Etage();  
				
	
		EcranGraphique.clear();
		EcranGraphique.setClearColor(255,255,255);
		EcranGraphique.init(100,50,700,700,650,650,"Ascenceur");


		
		while (end == false) {

			EcranGraphique.clear();
			EcranGraphique.setColor(0,0,0);

			int cpt = ti.nE -1;

			for (y=30; y<ti.nE*30+1; y+=30) {
				EcranGraphique.drawLine(200,y,400,y);
				EcranGraphique.drawText(5,y,3, cpt," etages"); 
		                EcranGraphique.drawRect(440,y-20,10,10);
				cpt--;
				}
		        
		        
			
			EcranGraphique.drawLine(90,0,90,y-30);
			EcranGraphique.drawLine(190,0,190,y-30);
		        EcranGraphique.drawLine(410,0,410,y-30);
		        EcranGraphique.drawLine(420,0,420,y-30);
		              
		
		
			presence(ti, tE, ei); 
				
			y = 0;	
			for (i = ti.nE-1 ; i>=0 ; i--) {
				EcranGraphique.drawText (280, 25+y , 3 , tE[i].nbP ," personne(s)");

				x = 0;
				j = 0;
				while (x < tE[i].nbP) {
					EcranGraphique.drawLine(210+j,y+15,210+j,y+28);
					x++; 
					j+=4;
				} 
				y+=30;  
			}
	


			ap = appel(ti, tE, tEA, tNbPE);
		
			if (ap == true) {
	

				testMax = testAppelPlusHaut (ai, tEA);
				testMin = testAppelPlusBas (ai, tEA);
				testEgale = testAppelEgale (ai, tEA);
			

			
			
				if (ai.dir == 1) {
					if ( testMax == true) {
						deplacement(ai);
						monte (ai, tE, tEA, tNbPE);
						descend (ai, tE, tEA, tNbPE);
					}
					else if (testMax == false && testMin == true) {
						ai.dir = -1;
					}
					else {
						ai.dir = 0;
					}
				
					EcranGraphique.drawLine(530,50,530,85);
					EcranGraphique.drawLine(530,50,525,55);
					EcranGraphique.drawLine(530,50,535,55);
	 			}
			
				else if (ai.dir == -1) {
					if (testMin == true) {
						deplacement(ai);
						monte (ai, tE, tEA, tNbPE);
						descend (ai, tE, tEA, tNbPE);
					}
					else if (testMin == false && testMax == true) {
						ai.dir = 1;
					}
					else {
						ai.dir = 0;
					}
				
					EcranGraphique.drawLine(530,50,530,85);
					EcranGraphique.drawLine(525,80,530,85);
					EcranGraphique.drawLine(535,80,530,85);
				}	
				
				else if (ai.dir == 0) {
					if (testEgale == true) {
						monte (ai, tE, tEA, tNbPE);
					}
					else if (testMin == false && testMax == true) {
						ai.dir = 1;
					}
					else if (testMax == false && testMin == true) {
						ai.dir = -1;
					}
				}
				
				x = 0;
				j = 0;

					

				while (x < ai.nbP) {
					EcranGraphique.drawLine(104+j, ai.pos+10, 104+j, ai.pos+28);
					x++;
					j+=4;
				}
			
	
				EcranGraphique.drawRect (100,ai.pos,80,30);




			}
			else {
				EcranGraphique.drawRect (100,ai.pos,80,30);	
				EcranGraphique.drawLine(510,70,550,70);
				EcranGraphique.drawLine(510,70,515,65);
				EcranGraphique.drawLine(510,70,515,75);
				EcranGraphique.drawLine(545,75,550,70);
				EcranGraphique.drawLine(545,65,550,70);
			}
		


			y = 30;
			for (i = ti.nE-1 ; i>=0 ; i--) {
				if (tEA[i] == true) {
					EcranGraphique.fillRect(440,y-20,10,10);
				}
				y+=30;
			}
			
			temps++;
			EcranGraphique.setColor(255,0,0);
			EcranGraphique.drawText(500,20,3, "Temps : "+temps);
			EcranGraphique.drawText(490,40,3, ai.nbP, " Personne(s)");
			EcranGraphique.flush();
			EcranGraphique.wait(250); 


			}
		                


                     
		
		EcranGraphique.flush();
	
	} 
	
	/** 
	* Fonction main
	* @param args 
	* @return void aucun retour
	**/
	
	
public static void main (String [] args) {
	
	Tour t = new Tour();
	Etage e = new Etage();
	Ascenceur a = new Ascenceur();
	int choix ;
	
	do {
		
		Ecran.afficher (" 0 : Quitter\n 1 : Choisir la probabilite d'arrivee aux etages (par defaut : 0.002) \n 2 : Choisir le nombre maximum de passagers (par defaut : 10)\n 3 : Choisir le nombre d'etage (max 100, par defaut : 20) \n 4 : Lancer simulation\n Choix : ");
		
		choix = Clavier.saisirInt();
		
		if (choix == 1){
			Ecran.afficher ("Choisir la probabilite : ");
			e.pbE = Clavier.saisirDouble(); }
		else if (choix == 2) {
			Ecran.afficher ("Choisir nombre de passager max : "); 
			a.nPMax = Clavier.saisirInt(); }
		else if(choix == 3){
			Ecran.afficher ("Choisir nombre d'etages (max 100) : "); 
			t.nE = Clavier.saisirInt(); 
			if (t.nE <0 || t.nE >NE_MAX) {
				do {
					Ecran.afficher ("Valeur non conforme, reessayez : ");
					t.nE = Clavier.saisirInt(); 
				} while (t.nE <0 && t.nE >NE_MAX); }}
			
		Ecran.sautDeLigne();
	} while ( choix != 0 && choix != 4);
	
	a.nE = t.nE;
	
	if (choix == 4){
		
		affichage (t, e, a);
	}
	
}
}
