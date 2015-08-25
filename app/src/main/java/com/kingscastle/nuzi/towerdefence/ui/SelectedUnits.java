package com.kingscastle.nuzi.towerdefence.ui;

import android.graphics.Color;

import com.kingscastle.nuzi.towerdefence.gameElements.GameElement;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.LivingThing;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.nuzi.towerdefence.gameElements.livingThings.buildings.Building;
import com.kingscastle.nuzi.towerdefence.gameUtils.vector;

import java.util.ArrayList;


public class SelectedUnits {

	private LivingThing selectedBuilding;
	private Humanoid selectedUnit;
	private GameElement selectedThing;
	private ArrayList<? extends GameElement> selectedThings;




	public void setSelected(LivingThing lt) {
		if (lt instanceof Building)
			setSelectedBuilding(lt);
		else
			setSelectedThing(lt);
	}

	public void setSelectedThing(GameElement ge) {
		if (selectedThing != null)
			selectedThing.setSelected(false);

		selectedThing = ge;

		if (selectedThing != null)
			selectedThing.setSelected(true);
	}

	public void setSelected(ArrayList<? extends GameElement> ges) {
		clearSelected();

		if( ges != null ) {
			for( GameElement ge : ges) {
				ge.setSelected(true);
				if( ge instanceof LivingThing )
					((LivingThing)ge).setSelectedColor(Color.YELLOW);
			}

			selectedThings = ges;
		}
	}

/*
	public void setSelectedUnit(LivingThing u) {
		clearSelected();

		selectedUnit = u;
		selectedThing = u;
		if (selectedUnit != null) {

			selectedUnit.setSelected(true);
			selectedUnit.setSelectedColor(Color.YELLOW);
		}
	}*/

	public void setSelectedBuilding(LivingThing b) {
		clearSelected();

		selectedBuilding = b;
		selectedThing = b;
		if (selectedBuilding != null) {
			selectedBuilding.setSelected(true);
			selectedBuilding.setSelectedColor(Color.YELLOW);
		}
	}


	public void clearSelected() {
		if (selectedThing != null) {
			selectedThing.setSelected(false);
			// selectedBuilding.hideHealthPercentage();
			selectedThing = null;
		}

		clearSelectedBuilding();

		clearSelectedUnit();

		clearSelectedThings();

	}

	public void clearSelectedBuilding() {
		LivingThing selectedBuilding = this.selectedBuilding;
		if (selectedBuilding == null)
			return;

		selectedBuilding.setSelected(false);
		this.selectedBuilding = null;
	}

	public void clearSelectedUnit() {
		LivingThing selectedUnit = this.selectedUnit;
		if (selectedUnit == null)
			return;

		selectedUnit.setSelected(false);
		this.selectedUnit = null;
	}

	public void clearSelectedThings() {
		ArrayList<? extends GameElement> selectedThings = this.selectedThings;
		if (selectedThings == null)
			return;

		for (GameElement ge : selectedThings)
			ge.setSelected(false);

		this.selectedThings = null;
	}





	public synchronized void setUnSelected(GameElement ge) {
		if (ge == null)
			return;
		if (selectedUnit == ge) {
			selectedUnit = null;
			ge.setSelected(false);
			return;
		} else if (selectedBuilding == ge) {
			selectedBuilding = null;
			ge.setSelected(false);
		} else if (selectedThing == ge) {
			selectedThing = null;
			ge.setSelected(false);
		}

		if (selectedThings == null)
			return;
		else if (selectedThings.remove(ge)) {
			ge.setSelected(false);
		}

		if (selectedThings.isEmpty()) {
			selectedThings = null;
		}
	}




	public Humanoid getSelectedUnit() {
		if (selectedUnit != null)
			selectedUnit.setSelected(true);

		return selectedUnit;
	}




	public void moveSelected(vector inDirection) {
		if (getSelectedUnit() != null) {
			getSelectedUnit().walkToAndStayHereAlreadyCheckedPlaceable(null);
			getSelectedUnit().getLegs().act(inDirection, true);
		}
	}

	//	public boolean moveSelectedUnits(Vector dest) {
	//		if( getSelectedUnits() != null ) {
	//			return SquareFormation.staticMoveTroops(getSelectedUnits(), dest);
	//
	//		} else if (getSelectedSquad() != null) {
	//			return getSelectedSquad().setGroupHere(dest);
	//		}
	//
	//		return false;
	//	}



	public boolean somethingIsSelected() {
		if (getSelectedThing() != null || getSelectedUnits() != null
				|| getSelectedUnit() != null || getSelectedBuilding() != null
				)
			return true;
		else
			return false;
	}

	public boolean multipleThingsAreSelected() {
		if (getSelectedUnits() != null)
			return true;
		else
			return false;
	}

	public LivingThing getSelectedBuilding() {
		return selectedBuilding;
	}

	public GameElement getSelectedThing() {
		return selectedThing;
	}

	public ArrayList<? extends GameElement> getSelectedUnits() {
		return selectedThings;
	}

}
